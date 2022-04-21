package com.yugabyte.mybatis.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;

import com.yugabyte.mybatis.model.OrderLine;

public class OrderLineDAO extends GenericDAO  implements DAO <OrderLine, Integer> {
    @Override
    public void save(OrderLine entity) {
        try (SqlSession session = openCurrentSession()) {
            try {
                session.insert("mybatis.mapper.OrderLineMapper.save", entity);
            } catch( Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Optional<OrderLine> findById(Integer integer) {
        throw new UnsupportedOperationException();
    }

    public List<OrderLine> findAllForOrder(final UUID orderId) {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.OrderLineMapper.findAllForOrder", orderId);
        }
    }

    @Override
    public List<OrderLine> findAll() {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.OrderLineMapper.findAll");
        }

    }

}
