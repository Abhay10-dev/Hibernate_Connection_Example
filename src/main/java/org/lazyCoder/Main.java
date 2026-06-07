package org.lazyCoder;

import org.lazyCoder.db.DBManage;
import org.lazyCoder.model.Employee;
import org.lazyCoder.model.Laptop;
import org.lazyCoder.service.Services;

public class Main {
    public static void main(String[] args) {

//        // Create DB if not exists
//        // After creating DB, comment the below code to avoid creating DB again and again
//        // And update your DB name in hibernate.cfg.xml file
//        DBManage dbManage = new DBManage();
//        dbManage.createDB("employee_laptop_db");


        //Create services instance
        Services services = new Services();


//        // Create Employee
//        services.createEmployee("Aakash", 50000);
//        // Create Laptop
//        services.createLap("Dell", 30000);


//        // Get employee and laptop by id
//        Employee employeeById = services.getEmployeeById(1);
//        System.out.println(employeeById);
//        Laptop laptopById = services.getLaptopById(1);
//        System.out.println(laptopById);


//        // Update employee and laptop by id
//        services.updateEmployee(2, "Bob", 120000);
//        services.updateLaptop(2, "Asus", 120000);


//        // Unassigning the link
//        services.unassignLap(1);
//        services.unassignLap(2);


//        // Assigning link
//        services.assignLap(3, 4);
//        services.assignLap(4, 5);
//        services.assignLap(5, 3);


//        // Delete employee and laptop by id
//        services.deleteEmployee(6);
//        services.deleteLaptop(6);

    }
}