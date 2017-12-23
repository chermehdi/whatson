package com.mql.whatson.entity.dao;

import java.util.List;

import com.mql.whatson.entity.Role;

public interface RoleDao {

    Role save(Role role);

    void remove(Role role);

    void update(Role role);

    Role findById(Long roleId);

    List<Role> findAll();

    Role findByRoleName(Role.RoleName name);

}
