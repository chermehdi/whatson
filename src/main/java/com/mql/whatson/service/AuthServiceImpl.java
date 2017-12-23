package com.mql.whatson.service;


import com.mql.whatson.entity.RegisterationRequest;
import com.mql.whatson.entity.Role;
import com.mql.whatson.entity.Token;
import com.mql.whatson.entity.User;
import com.mql.whatson.entity.dao.RoleDao;
import com.mql.whatson.entity.dao.TokenDao;
import com.mql.whatson.entity.dao.UserDao;
import com.mql.whatson.service.helpers.PasswordHasher;
import com.mql.whatson.service.helpers.UserFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author Mehdi Maick
 */
public class AuthServiceImpl implements AuthService {

    @Inject
    PasswordHasher hasher;

    @Inject
    UserDao userDao;

    @Inject
    TokenDao tokenDao;

    @Inject
    RoleDao rolesDao;

    @Inject
    UserFactory userFactory;


    @Override
    public User userHaveAccount(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user;
        if ((user = userHaveAccount(email)) == null) return null;
        String hashedPassword = hasher.getHash(password);
        if (user.getPassword() != null &&
                user.getPassword().equals(hashedPassword) &&
                user.isActive()) {
            return user;
        }
        return null;
    }

    @Override
    public boolean registerUser(RegisterationRequest request) {
        if (userHaveAccount(request.getEmail()) != null) return false;
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hasher.getHash(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Token validationToken = new Token(UUID.randomUUID().toString());
        tokenDao.save(validationToken);
        user.setToken(validationToken);
        Role userRole = getUserRole();
        user.setRoles(new HashSet<>(Collections.singletonList(userRole))); // TODO: should add roles
        userDao.save(user);
        return true;
    }

    @Override
    public User registerUserGoogle(RegisterationRequest registrationRequest) {
        User user;
        if ((user = userHaveAccount(registrationRequest.getEmail())) != null) return user;
        Role userRole = getUserRole();
        user = userFactory.of(registrationRequest, new HashSet<>(Collections.singletonList(userRole)));
        user.setGoogle(true);
        userDao.save(user);
        return user;
    }

    @Override
    public User registerUserFacebook(RegisterationRequest registrationRequest) {
        User user;
        if ((user = userHaveAccount(registrationRequest.getEmail())) != null) return user;
        Role userRole = getUserRole();
        user = userFactory.of(registrationRequest, new HashSet<>(Collections.singletonList(userRole)));
        user.setFacebook(true);
        userDao.save(user);
        return user;
    }

    private Role getUserRole() {
        try {
            return rolesDao.findByRoleName(Role.RoleName.USER);
        } catch (Exception e) {
            Role userRole = new Role(Role.RoleName.USER);
            rolesDao.save(userRole);
            return userRole;
        }
    }
}
