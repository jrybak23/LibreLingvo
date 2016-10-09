package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Created by igorek2312 on 23.09.16.
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role, Integer> implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Qualifier("findRoleByNameCriteriaQuery")
    private CriteriaQuery<Role> findRoleByNameCriteriaQuery;

    @Override
    public Role findByName(String roleName) {
        return entityManager.createQuery(findRoleByNameCriteriaQuery)
                .setParameter("name", roleName).getSingleResult();
    }
}
