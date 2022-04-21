package com.yugabyte.mybatis.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.yugabyte.mybatis.model.User;

public class UserDAO extends GenericDAO implements DAO < User, Long > {

    @Override
    public void save(final User entity) {
        SqlSession session = openCurrentSession();
        try {
        	session.insert("mybatis.mapper.UserMapper.save", entity);
        	session.commit();
         } catch (RuntimeException rte) {
         }  
        session.close();
    }

    @Override
    public Optional <User> findById(final Long id) {
        return Optional.ofNullable(openCurrentSession().selectOne("mybatis.mapper.UserMapper.findByID", id));
    }

    @Override
    public List <User> findAll() {
        try (SqlSession session = openCurrentSession()) {
            return session.selectList("mybatis.mapper.UserMapper.findAll");
        }
    }

    public void delete(final User user) {
        SqlSession session = openCurrentSession();
        try {
            session.delete("mybatis.mapper.UserMapper.delete", user.getUserId());
        } catch (RuntimeException rte) {
            throw rte;
        }
        session.close();
    }

}