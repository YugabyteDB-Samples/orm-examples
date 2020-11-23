package com.yugabyte.hibernatedemo.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.yugabyte.hibernatedemo.model.Customer;

public class CustomerDAO extends GenericDAO implements DAO <Customer, Long> {

    @Override
    public void save(final Customer entity) {
        Session session = openCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(entity);
            transaction.commit();
        } catch(RuntimeException rte) {
            transaction.rollback();
        }
        session.close();
    }

    @Override
    public Optional<Customer> findById(final Long id) {
        return Optional.ofNullable(openCurrentSession().get(Customer.class, id));
    }

    @Override
    public List<Customer> findAll() {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from User", Customer.class)
            		.setFirstResult(0)
            		.setMaxResults(2)
            		.list();
        }
    }

}
