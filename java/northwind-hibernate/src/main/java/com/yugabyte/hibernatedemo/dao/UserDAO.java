package com.yugabyte.hibernatedemo.dao;

import com.yugabyte.hibernatedemo.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAO extends GenericDAO implements DAO <User, Long> {

    @Override
    public void save(final User entity) {
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
    public Optional<User> findById(final Long id) {
        return Optional.ofNullable(openCurrentSession().get(User.class, id));
    }

    @Override
    public List<User> findAll() {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from User", User.class)
            		.setFirstResult(0)
            		.setMaxResults(2)
            		.list();
        }
    }

}
