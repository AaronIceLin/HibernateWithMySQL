package com.aaron.app;

import com.aaron.persistence.HibernateUtil;
import com.aaron.util.ManageStudent;
import org.hibernate.SessionFactory;

/**
 * Created by Aaron on 7/15/2017.
 */
public class StudentApplication {

    public static void main(String[] args){

        System.out.println("Student + Hibernate + MySQL");
        SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();

        ManageStudent manageStudent = new ManageStudent();

        //Clean up table
        manageStudent.emptyStudentTable(sessionFactory);

        //Add Student
        int stu1 = manageStudent.addStudent(sessionFactory,"Eric","Apple",22);
        int stu2 = manageStudent.addStudent(sessionFactory,"Anna","Flower",25);
        int stu3 = manageStudent.addStudent(sessionFactory,"John","Flower",15);
        int stu4 = manageStudent.addStudent(sessionFactory,"Aaron","Flower",35);

        manageStudent.listStudents(sessionFactory);
        manageStudent.deleteStudent(sessionFactory,stu1);
        manageStudent.updateStudent(sessionFactory,stu3,8);
        manageStudent.listStudents(sessionFactory);

        //Clean up table
        manageStudent.emptyStudentTable(sessionFactory);
    }
}
