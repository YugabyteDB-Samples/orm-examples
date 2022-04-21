package com.yugabyte.mybatis.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.yugabyte.mybatis.model.Product;

public class ProductDAO extends GenericDAO implements DAO < Product, Long > {
    @Override
    public void save(Product entity) {
        try (SqlSession session = openCurrentSession()) {
            try {
                session.insert("mybatis.mapper.ProductMapper.save", entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Optional < Product > findById(Long id) {
        return Optional.ofNullable(openCurrentSession().selectOne("mybatis.mapper.ProductMapper.findByID", id));
    }

    @Override
    public List < Product > findAll() {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.ProductMapper.findAll");
        }
    }

    public void delete(final Product product) {
        SqlSession session = openCurrentSession();
        try {
        	session.delete("mybatis.mapper.OrderMapper.delete", product.getProductId());
        } catch (RuntimeException rte) {
            throw rte;
        }
        session.close();
    }
}