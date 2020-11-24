package com.yugabyte.hibernatedemo.dao;

import com.yugabyte.hibernatedemo.model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
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

    public List<Order> findOrdersForUser(final String customerId) {
        try (Session session = openCurrentSession()) {
        	
        	String hql = "FROM Order WHERE customer_id = :customer_id";
        	return session
        			.createQuery(hql, Order.class)
        			.setParameter("customer_id", customerId)
        			.list();
//            return session.createQuery("from orders where customer_id = " + customerId, Order.class).list();
        }

    }

    @Override
    public List<Order> findAll() {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from Order", Order.class)
            		.setFirstResult(0)
            		.setMaxResults(2)
            		.list();
        }
    }

}
