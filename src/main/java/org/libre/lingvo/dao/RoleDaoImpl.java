package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by igorek2312 on 23.09.16.
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {
    @Override
    public Optional<Role> findByName(String roleName) {
        Role role = (Role) getSession().createQuery("from org.libre.lingvo.entities.Role role where " +
                "role.name = :roleName")
                .setParameter("roleName", roleName)
                .uniqueResult();
        return Optional.ofNullable(role);
    }
}
