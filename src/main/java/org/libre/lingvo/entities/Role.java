package org.libre.lingvo.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 08.09.16.
 */

@Entity
public class Role implements GrantedAuthority, Serializable {

    private Integer id;

    private String name;

    private Set<UserRole> userRoles = new HashSet<>();

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "pk.role")
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    @Transient
    public String getAuthority() {
        return name;
    }
}

