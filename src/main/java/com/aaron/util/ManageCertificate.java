package com.aaron.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by Aaron on 7/14/2017.
 */
public class ManageCertificate {

    /***
     * Empty Certificate table
     * @param sessionFactory
     */
    public void emptyCertificateTable(SessionFactory sessionFactory){
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            int result  = session.createQuery("DELETE FROM Certificate").executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
