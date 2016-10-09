package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Role;

/**
 * Created by igorek2312 on 23.09.16.
 */
public interface RoleDao extends GenericDao<Role,Integer> {
    Role findByName(String roleName);
}
