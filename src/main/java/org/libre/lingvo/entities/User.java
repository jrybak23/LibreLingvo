package org.libre.lingvo.entities;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 08.09.16.
 */

@Entity
public class User implements Serializable {

    private Long id;

    private String email;

    private String name;

    private String password;

    private Boolean enabled=false;

    private Set<UserRole> userRoles=new HashSet<>();

    private VerificationToken verificationToken;

    public User() {
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled=user.isEnabled();
        this.userRoles = user.getUserRoles();
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty
    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    public VerificationToken getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.verificationToken = verificationToken;
    }
}
