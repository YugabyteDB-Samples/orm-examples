package com.yugabyte.hibernatedemo.dao;

import com.yugabyte.hibernatedemo.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class ProductDAO extends GenericDAO  implements DAO <Product, Long> {
    @Override
    public void save(Product entity) {
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
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(openCurrentSession().get(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        try (Session session = openCurrentSession()) {
            return session.createQuery("from Product", Product.class).list();
        }
    }

    public void delete(final Product product){
          Session session = openCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(product);
            transaction.commit();
        } catch(RuntimeException rte) {
            transaction.rollback();
        }
        session.close();
    }
}
