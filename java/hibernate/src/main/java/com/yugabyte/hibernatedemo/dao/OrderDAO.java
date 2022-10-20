package com.yugabyte.hibernatedemo.dao;

import com.yugabyte.hibernatedemo.model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.*;
import java.util.Optional;

public class OrderDAO extends GenericDAO  implements DAO <Order, Integer> {
    @Override
    public void save(Order entity) {
        try (Session session = openCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(entity);
            } catch( Exception e) {
                e.printStackTrace();
                transaction.rollback();
            } finally {
                transaction.commit();
            }
        }

    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.ofNullable(openCurrentSession().get(Order.class, id.longValue()));
    }

    public Optional<Order> find(UUID id) {
        return Optional.ofNullable(openCurrentSession().get(Order.class, id));
    }

    public List<Order> findOrdersForUser(final Integer userId) {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from Order where user_id = " + userId, Order.class).list();
        }

    }

    public void delete(final Order order){
          Session session = openCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(order);
            transaction.commit();
        } catch(RuntimeException rte) {
            transaction.rollback();
            throw rte;
        }
        session.close();
    }

    @Override
    public List<Order> findAll() {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from Order", Order.class).list();
        }
    }

}
