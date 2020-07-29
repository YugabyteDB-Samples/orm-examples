package com.yugabyte.hibernatedemo.dao;

import com.yugabyte.hibernatedemo.server.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

class GenericDAO {
    Session openCurrentSession() {
        return getSessionFactory().openSession();
    }
    private static SessionFactory getSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
}
