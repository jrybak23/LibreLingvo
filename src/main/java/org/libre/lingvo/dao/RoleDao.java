package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Role;

import java.util.Optional;

/**
 * Created by igorek2312 on 23.09.16.
 */
public interface RoleDao extends GenericDao<Role,Integer> {
    Optional<Role> findByName(String roleName);
}
