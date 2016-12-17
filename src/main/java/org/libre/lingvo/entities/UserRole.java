package org.libre.lingvo.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by igorek2312 on 08.10.16.
 */
@Entity
@AssociationOverrides(value = {
        @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "userId")),
        @AssociationOverride(name = "pk.role", joinColumns = @JoinColumn(name = "roleId"))
})
public class UserRole implements Serializable {
    private UserRoleId pk=new UserRoleId();

    public UserRole() {
    }

    public UserRole(User user,Role role) {
        pk.setUser(user);
        pk.setRole(role);
    }

    @EmbeddedId
    public UserRoleId getPk() {
        return pk;
    }

    public void setPk(UserRoleId pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return pk.getUser();
    }

    public void setUser(User user) {
        pk.setUser(user);
    }

    @Transient
    public Role getRole() {
        return pk.getRole();
    }

    public void setRole(Role role) {
        pk.setRole(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        return pk != null ? pk.equals(userRole.pk) : userRole.pk == null;

    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
