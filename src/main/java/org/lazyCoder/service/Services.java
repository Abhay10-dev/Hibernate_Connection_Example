package org.lazyCoder.service;


import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.lazyCoder.condig.HibernateConnect;
import org.lazyCoder.exception.NoEmployeeFoundException;
import org.lazyCoder.model.Employee;
import org.lazyCoder.model.Laptop;

public class Services {

    public void createEmployee(String empName, double empSalary){
        try (
                Session session = HibernateConnect.getSessionFactory().openSession()
        )
        {
            Transaction transaction = session.beginTransaction();
            session.persist(new Employee(empName, empSalary));
            transaction.commit();

        } catch (SessionException e){
            System.out.println("Unable to create session : " + e.getMessage());
        }
    }

    public void createLap(String lapName, double lapPrice){
        try (
                Session session = HibernateConnect.getSessionFactory().openSession()
        )
        {
            Transaction transaction = session.beginTransaction();
            session.persist(new Laptop(lapName, lapPrice));
            transaction.commit();
        } catch (SessionException e){
            System.out.println("Unable to create session : " + e.getMessage());
        }
    }

    public void assignLap(int empId, int lapId) {
        try (
                Session session = HibernateConnect.getSessionFactory().openSession()
        ) {
            Transaction transaction = session.beginTransaction();
            Laptop laptop = session.find(Laptop.class, lapId);
            Employee employee = session.find(Employee.class, empId);

            if(employee == null) {
                throw new NoEmployeeFoundException(
                        "Unable to find employee with id " + empId);
            }

            if(laptop == null) {
                throw new RuntimeException(
                        "Unable to find laptop with id " + lapId);
            }

            employee.setLaptop(laptop);

            transaction.commit();
        }
    }
}
