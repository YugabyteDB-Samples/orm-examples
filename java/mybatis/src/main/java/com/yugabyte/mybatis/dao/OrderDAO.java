package com.yugabyte.mybatis.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;

import com.yugabyte.mybatis.model.Order;

public class OrderDAO extends GenericDAO  implements DAO <Order, Integer> {
    @Override
    public void save(Order entity) {
        try (SqlSession session = openCurrentSession()) {
            try {
                session.insert("mybatis.mapper.OrderMapper.save", entity);
            } catch( Exception e) {
                e.printStackTrace();
            } 
        }

    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.ofNullable(openCurrentSession().selectOne("mybatis.mapper.OrderMapper.findByID", id));
    }

    public Optional<Order> find(UUID id) {
        return Optional.ofNullable(openCurrentSession().selectOne("mybatis.mapper.OrderMapper.findByID", id));
    }

    public List<Order> findOrdersForUser(final Integer userId) {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.OrderMapper.findOrdersForUser", userId);
        }

    }

    public void delete(final Order order){
          SqlSession session = openCurrentSession();
        try {
            session.delete("mybatis.mapper.OrderMapper.delete", order.getOrderId());
        } catch(RuntimeException rte) {
       
        }
        session.close();
    }

    @Override
    public List<Order> findAll() {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.OrderMapper.findAll");
        }
    }

}
