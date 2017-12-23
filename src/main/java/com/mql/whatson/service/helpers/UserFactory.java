package com.mql.whatson.service.helpers;

import com.mql.whatson.entity.RegisterationRequest;
import com.mql.whatson.entity.Role;
import com.mql.whatson.entity.User;

import java.util.Set;

/**
 * creates user out of different DTO objects
 *
 * @author Mehdi Maick
 */
public class UserFactory {


    public User of(RegisterationRequest request, Set<Role> roles) {
        User user = new User();
        user.setActive(false);
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRoles(roles);
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setToken(null);
        user.setPicture(request.getPicture());
        return user;
    }
}
