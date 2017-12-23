package com.mql.whatson.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mql.whatson.entity.Role;

@Stateless
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Role save(Role role) {
        try {
            entityManager.persist(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    public void remove(Role role) {
        try {
            entityManager.remove(entityManager.merge(role));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Role role) {
        try {
            entityManager.merge(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Role findById(Long roleId) {
        try {
            return entityManager.find(Role.class, roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Role> findAll() {

        try {
            TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM ROLE r" + " WHERE ORDER BY r.roleId",
                    Role.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Role findByRoleName(Role.RoleName name) {
        TypedQuery<Role> roleQuery = entityManager.createQuery("select r from Role r where r.name = :rolename", Role.class);
        roleQuery.setParameter("rolename", name);
        try {
            return roleQuery.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
