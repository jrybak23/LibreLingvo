package org.libre.lingvo.config.criteria.queries;

import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.User_;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;


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
        ParameterExpression<String> parameter = cb.parameter(String.class, "templates");
        cq.where(cb.equal(userRoot.get(User_.email),parameter));

        return cq;
    }

    @Bean
    CriteriaQuery<User> findUsersByEmailSubstring(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        ParameterExpression<String> parameter = cb.parameter(String.class, "emailSubstring");
        Expression<String> patern = cb.concat(cb.concat("%", parameter), "%");
        cq.where(cb.like(userRoot.get(User_.email),patern));
        return cq;
    }
}
