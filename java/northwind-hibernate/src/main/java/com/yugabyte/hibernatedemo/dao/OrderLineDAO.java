//package com.yugabyte.hibernatedemo.dao;
//
//import com.yugabyte.hibernatedemo.model.OrderLine;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public class OrderLineDAO extends GenericDAO  implements DAO <OrderLine, Integer> {
//    @Override
//    public void save(OrderLine entity) {
//        try (Session session = openCurrentSession()) {
//            Transaction transaction = session.beginTransaction();
//            try {
//                session.save(entity);
//            } catch( Exception e) {
//                e.printStackTrace();
//                transaction.rollback();
//            } finally {
//                transaction.commit();
//            }
//        }
//
//    }
//
//    @Override
//    public Optional<OrderLine> findById(Integer integer) {
//        throw new UnsupportedOperationException();
//    }
//
//    public List<OrderLine> findAllForOrder(final UUID orderId) {
//        try (Session session = openCurrentSession()) {
//            return session.createQuery("from OrderLine where orderId = '" + orderId + "'", OrderLine.class).list();
//        }
//    }
//
//    @Override
//    public List<OrderLine> findAll() {
//        try (Session session = openCurrentSession()) {
//            return session.createQuery("from OrderLine", OrderLine.class).list();
//        }
//
//    }
//
//}
