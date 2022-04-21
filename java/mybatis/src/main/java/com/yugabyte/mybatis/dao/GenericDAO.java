package com.yugabyte.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.yugabyte.mybatis.server.MybatisUtil;

class GenericDAO {
    SqlSession openCurrentSession() {
        return getSessionFactory().openSession();
    }
    private static SqlSessionFactory getSessionFactory() {
        return MybatisUtil.getSessionFactory();
    }
}
