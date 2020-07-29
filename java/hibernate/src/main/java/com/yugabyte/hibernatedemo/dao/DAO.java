package com.yugabyte.hibernatedemo.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface DAO <T, Id extends Serializable> {

    void save(T entity);

    Optional<T> findById(Id id);

    List<T> findAll();

}
