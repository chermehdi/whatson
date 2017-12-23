package com.mql.whatson.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mql.whatson.entity.User;

@Stateless
public class UserDaoImpl implements UserDao {

    @PersistenceContext(name = "")
    private EntityManager entityManager;

    public UserDaoImpl() {
    }

    public User findById(Long userId) {
        try {
            return entityManager.find(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findAll() {

        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.userId", User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User save(User user) {
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void remove(User user) {
        try {
            entityManager.remove(entityManager.merge(user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try {
            entityManager.merge(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("Select r from User r where r.email=:email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("the error is " + e.getMessage());
        }
        return null;
    }

}
