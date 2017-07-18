package com.aaron.util;

import com.aaron.common.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Aaron on 7/15/2017.
 */
public class ManageStudent {

    /***
     * Add Student into table
     * @param sessionFactory
     * @param firstName
     * @param lastName
     * @param age
     * @return
     */
    public Integer addStudent(SessionFactory sessionFactory,String firstName, String lastName, int age){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer studentId = null;

        try{
            transaction = session.beginTransaction();
            Student student = new Student(firstName,lastName,age);
            studentId = (Integer)session.save(student);
            transaction.commit();

        }catch (HibernateException he){
            if(transaction!=null)   transaction.rollback();
            he.printStackTrace();
        }finally {
            session.close();
        }

        return studentId;
    }

    /***
     * List of students
     * @param sessionFactory
     */
    public void listStudents(SessionFactory  sessionFactory ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List students = session.createQuery("FROM Student").list();
            for (Iterator iterator =
                 students.iterator(); iterator.hasNext();){
                Student student = (Student) iterator.next();
                System.out.println("\n********************************************************");
                System.out.print("Student First Name: " + student.getFirstName());
                System.out.print("  Last Name: " + student.getLastName());
                System.out.print("  Age: " + student.getAge());
                System.out.println("\n********************************************************\n");
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
     * Update student record
     * @param sessionFactory
     * @param studentId
     * @param age
     */
    public void updateStudent(SessionFactory  sessionFactory,Integer studentId, int age ){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Student student =
                    (Student) session.get(Student.class, studentId);
            student.setAge( age );
            session.update(student);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /***
     * Delete student by id
     * @param sessionFactory
     * @param studentId
     */
    public void deleteStudent(SessionFactory  sessionFactory, Integer studentId){
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Student student =
                    (Student) session.get(Student.class, studentId);
            session.delete(student);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /***
     * Empty student table
     * @param sessionFactory
     */
    public void emptyStudentTable(SessionFactory  sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            int result  = session.createQuery("DELETE FROM Student").executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
