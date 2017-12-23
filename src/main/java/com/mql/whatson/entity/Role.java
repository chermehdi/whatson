package com.mql.whatson.entity;

import java.io.Serializable;
//import java.util.List;
//import java.util.Vector;
import java.util.Set;


import javax.persistence.*;


@Entity
@Table(name = "ROLE")
public class Role implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Long roleId;

    public enum RoleName {
        ADMIN,
        USER

    }
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @ManyToMany
    @JoinTable(name = "JOIN_USER_ROLE",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users; //Collection that contains no duplicated elements

    public Role() {
        super();
    }

    public Role(RoleName name){
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


}
