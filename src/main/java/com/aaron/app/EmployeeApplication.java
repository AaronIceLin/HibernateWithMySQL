package com.aaron.app;

import com.aaron.common.Certificate;
import com.aaron.persistence.HibernateUtil;
import com.aaron.util.ManageCertificate;
import com.aaron.util.ManageEmployee;
import org.hibernate.SessionFactory;

import java.util.HashSet;

/**
 * Hello world!
 *
 */
public class EmployeeApplication
{
    public static void main( String[] args )
    {

        /***
         *
         * create table EMPLOYEE (
         *      id INT NOT NULL auto_increment,
         *      first_name VARCHAR(20) default NULL,
         *      last_name  VARCHAR(20) default NULL,
         *      salary  INT  default NULL,
         *      PRIMARY KEY (id)
         * );
         */

        /***
         *
         * create table CERTIFICATE (
         *      id INT NOT NULL auto_increment,
         *      certificate_name VARCHAR(30) default NULL,
         *      employee_id INT default NULL,
         *      PRIMARY KEY (id)
         * );
         */

        System.out.println("Maven + Hibernate + MySQL");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        ManageEmployee manageEmployee = new ManageEmployee();

        manageEmployee.emptyEmployeeTable(sessionFactory);
        new ManageCertificate().emptyCertificateTable(sessionFactory);


        HashSet set1 = new HashSet();
        set1.add(new Certificate("MCA"));
        set1.add(new Certificate("MBA"));
        set1.add(new Certificate("PMP"));

      /* Add employee records in the database */
        Integer empID1 = manageEmployee.addEmployee(sessionFactory,"Manoj", "Kumar", 4000, set1);

      /* Another set of certificates for the second employee  */
        HashSet set2 = new HashSet();
        set2.add(new Certificate("BCA"));
        set2.add(new Certificate("BA"));
        set2.add(new Certificate("MCA"));
        set2.add(new Certificate("MBA"));
        set2.add(new Certificate("PMP"));

      /* Add another employee record in the database */
        Integer empID2 = manageEmployee.addEmployee(sessionFactory,"Dilip", "Kumar", 3000, set2);

      /* List down all the employees */
        manageEmployee.listEmployees(sessionFactory);

      /* Update employee's salary records */
        manageEmployee.updateEmployee(sessionFactory,empID1, 5000);

      /* Delete an employee from the database */
        manageEmployee.deleteEmployee(sessionFactory,empID2);

      /* List down all the employees */
        manageEmployee.listEmployees(sessionFactory);


        manageEmployee.emptyEmployeeTable(sessionFactory);
        new ManageCertificate().emptyCertificateTable(sessionFactory);

    }
}
