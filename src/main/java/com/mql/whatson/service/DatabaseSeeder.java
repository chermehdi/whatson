package com.mql.whatson.service;

import com.mql.whatson.entity.Role;
import com.mql.whatson.entity.dao.RoleDao;
import com.mql.whatson.entity.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * @author Mehdi Maick
 */
@Singleton
@Startup
public class DatabaseSeeder {

    @Inject
    UserDao userDao;

    @Inject
    RoleDao roleDao;


    Logger log = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        Role userRole = new Role(Role.RoleName.USER);
        Role adminRole = new Role(Role.RoleName.ADMIN);
        roleDao.save(userRole);
        roleDao.save(adminRole);
        log.info("created roles");
    }


}
