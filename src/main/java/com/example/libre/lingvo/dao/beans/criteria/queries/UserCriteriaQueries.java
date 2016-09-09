package com.example.libre.lingvo.dao.beans.criteria.queries;

import com.example.libre.lingvo.entities.User;
import com.example.libre.lingvo.entities.User_;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;


/**
 * Created by igorek2312 on 08.09.16.
 */

@Configuration
public class UserCriteriaQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    CriteriaQuery<User> findUserByEmailCriteriaQuery(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        ParameterExpression<String> parameter = cb.parameter(String.class,"email");
        cq.where(cb.equal(userRoot.get(User_.email),parameter));

        return cq;
    }
}
