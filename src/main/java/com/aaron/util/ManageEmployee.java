package com.aaron.util;

import com.aaron.common.Certificate;
import com.aaron.common.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Aaron on 7/13/2017.
 */
public class ManageEmployee {

    /***
     * Add employee
     * @param sessionFactory
     * @param fname
     * @param lname
     * @param salary
     * @param cert
     * @return
     */
    public Integer addEmployee(SessionFactory  sessionFactory,String fname, String lname, int salary, Set cert){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employee.setCertificates(cert);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeID;
    }

    /***
     * List of employees
     * @param sessionFactory
     */
    public void listEmployees(SessionFactory  sessionFactory ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext();){
                Employee employee = (Employee) iterator.next();
                System.out.println("\n********************************************************");
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.print("  Salary: " + employee.getSalary());
                System.out.println("\n********************************************************\n");

                Set certificates = employee.getCertificates();
                for (Iterator iterator2 =
                     certificates.iterator(); iterator2.hasNext();){
                    Certificate certName = (Certificate) iterator2.next();
                    System.out.println("Certificate: " + certName.getName());
                }
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /***
     * Update employee
     * @param sessionFactory
     * @param employeeId
     * @param salary
     */
    public void updateEmployee(SessionFactory  sessionFactory,Integer employeeId, int salary ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee employee =
                    (Employee)session.get(Employee.class, employeeId);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /***
     * Delete employee by id
     * @param sessionFactory
     * @param employeeId
     */
    public void deleteEmployee(SessionFactory  sessionFactory, Integer employeeId){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Employee employee =
                    (Employee)session.get(Employee.class, employeeId);
            session.delete(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /***
     * Empty employee table
     * @param sessionFactory
     */
    public void emptyEmployeeTable(SessionFactory  sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            int result  = session.createQuery("DELETE FROM Employee").executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
