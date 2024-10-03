package com.crud.dao;

import com.crud.models.User;

import java.util.ArrayList;
import java.util.Optional;

public interface DAO<T> {
    ArrayList<User> findAll();
    Optional<T> findById(Integer id);
    void save(T t);
    void update(T t);
    void delete(T t);
    void deleteById(Integer id);
}
