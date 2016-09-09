package com.example.libre.lingvo.dao;

import com.example.libre.lingvo.entities.User;
import com.example.libre.lingvo.entities.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Autowired
    @Qualifier("findUserByEmailCriteriaQuery")
    private CriteriaQuery<User> findUserByEmailCriteriaQuery;

    @Override
    public User findByEmail(String email) {
        return entityManager.createQuery(findUserByEmailCriteriaQuery)
                .setParameter("email", email).getSingleResult();
    }
}
