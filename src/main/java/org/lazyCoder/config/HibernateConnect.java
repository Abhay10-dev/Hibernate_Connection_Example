package org.lazyCoder.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.lazyCoder.model.Employee;
import org.lazyCoder.model.Laptop;

public class HibernateConnect {

    private static final SessionFactory sessionFactory = new Configuration()
                                                    .addAnnotatedClass(Employee.class)
                                                    .addAnnotatedClass(Laptop.class)
                                                    .configure("hibernate.cfg.xml")
                                                    .buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionAndSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.openSession().close();
            sessionFactory.close();
        }
    }
}
