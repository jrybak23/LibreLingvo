package org.libre.lingvo.dao;

import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Autowired
    @Qualifier("findUserByEmailCriteriaQuery")
    private CriteriaQuery<User> findUserByEmailCriteriaQuery;

    @Autowired
    @Qualifier("findUsersByEmailSubstring")
    private CriteriaQuery<User> findUsersByEmailSubstring;

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery(findUserByEmailCriteriaQuery)
                .setParameter("templates", email).getSingleResult();
    }

    @Override
    public List<User> findByEmailSubstring(String emailSubstring, Integer pageIndex, Integer maxRecords) {
        return entityManager.createQuery(findUsersByEmailSubstring)
                .setParameter("emailSubstring", emailSubstring)
                .setFirstResult(pageIndex-1)
                .setMaxResults(maxRecords)
                .getResultList();
    }

}
