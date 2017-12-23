package com.mql.whatson.entity.dao;

import java.util.List;

import com.mql.whatson.entity.User;

public interface UserDao {

    User findById(Long userId);

    User findByEmail(String email);

    List<User> findAll();

    User save(User user);

    void remove(User user);

    void update(User user);

}
