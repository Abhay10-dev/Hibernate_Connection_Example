package org.lazyCoder.service;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.lazyCoder.config.HibernateConnect;
import org.lazyCoder.exception.NoEmployeeFoundException;
import org.lazyCoder.model.Employee;
import org.lazyCoder.model.Laptop;

public class Services {

    // ==========================================
    // EMPLOYEE CRUD OPERATIONS
    // ==========================================

    // CREATE
    public void createEmployee(String empName, double empSalary){
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new Employee(empName, empSalary));
            transaction.commit();
            System.out.println("Employee created successfully!");
        } catch (SessionException e){
            System.out.println("Unable to create session : " + e.getMessage());
        }
    }

    // READ
    public Employee getEmployeeById(int empId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Employee employee = session.find(Employee.class, empId);
            if (employee == null) {
                throw new NoEmployeeFoundException("Unable to find employee with id " + empId);
            }
            return employee;
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void updateEmployee(int empId, String newName, double newSalary) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, empId);

            if (employee == null) {
                throw new NoEmployeeFoundException("Unable to find employee with id " + empId);
            }

            employee.setEmpName(newName);
            employee.setEmpSalary(newSalary);

            session.merge(employee); // Sync changes with database
            transaction.commit();
            System.out.println("Employee updated successfully!");
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // DELETE
    public void deleteEmployee(int empId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, empId);

            if (employee == null) {
                throw new NoEmployeeFoundException("Unable to find employee with id " + empId);
            }

            // If the employee has a laptop, break the relationship first to avoid constraint violations
            if (employee.getLaptop() != null) {
                employee.setLaptop(null);
            }

            session.remove(employee);
            transaction.commit();
            System.out.println("Employee deleted successfully!");
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ==========================================
    // LAPTOP CRUD OPERATIONS
    // ==========================================

    // CREATE
    public void createLap(String lapName, double lapPrice){
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(new Laptop(lapName, lapPrice));
            transaction.commit();
            System.out.println("Laptop created successfully!");
        } catch (SessionException e){
            System.out.println("Unable to create session : " + e.getMessage());
        }
    }

    // READ
    public Laptop getLaptopById(int lapId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Laptop laptop = session.find(Laptop.class, lapId);
            if (laptop == null) {
                throw new RuntimeException("Unable to find laptop with id " + lapId);
            }
            return laptop;
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void updateLaptop(int lapId, String newName, double newPrice) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Laptop laptop = session.find(Laptop.class, lapId);

            if (laptop == null) {
                throw new RuntimeException("Unable to find laptop with id " + lapId);
            }

            laptop.setLapName(newName);
            laptop.setLapPrice(newPrice);

            session.merge(laptop);
            transaction.commit();
            System.out.println("Laptop updated successfully!");
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // DELETE
    public void deleteLaptop(int lapId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Laptop laptop = session.find(Laptop.class, lapId);

            if (laptop == null) {
                throw new RuntimeException("Unable to find laptop with id " + lapId);
            }

            // Handle relationship unlinking if Laptop is the owned side or mapped bidirectionally
            // Note: If you have a foreign key constraint, you must set employee.setLaptop(null)
            // for the employee holding this laptop before deleting it.

            session.remove(laptop);
            transaction.commit();
            System.out.println("Laptop deleted successfully!");
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // ==========================================
    // ASSIGNMENT OPERATIONS (LINK / UNLINK)
    // ==========================================

    // ASSIGN / UPDATE ASSIGNMENT
    public void assignLap(int empId, int lapId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Laptop laptop = session.find(Laptop.class, lapId);
            Employee employee = session.find(Employee.class, empId);

            if(employee == null) {
                throw new NoEmployeeFoundException("Unable to find employee with id " + empId);
            }

            if(laptop == null) {
                throw new RuntimeException("Unable to find laptop with id " + lapId);
            }

            // Establish mapping
            employee.setLaptop(laptop);

            session.merge(employee);
            transaction.commit();
            System.out.println("Laptop " + lapId + " assigned to Employee " + empId);
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // REMOVE ASSIGNMENT (UNASSIGN)
    public void unassignLap(int empId) {
        try (Session session = HibernateConnect.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.find(Employee.class, empId);

            if(employee == null) {
                throw new NoEmployeeFoundException("Unable to find employee with id " + empId);
            }

            if (employee.getLaptop() != null) {
                employee.setLaptop(null); // Severing the link
                session.merge(employee);
                transaction.commit();
                System.out.println("Laptop unassigned from Employee " + empId);
            } else {
                System.out.println("Employee " + empId + " doesn't have any laptop assigned.");
            }
        } catch (SessionException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}