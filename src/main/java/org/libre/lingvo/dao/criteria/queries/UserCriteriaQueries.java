package org.libre.lingvo.dao.criteria.queries;

import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.User_;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.*;
import java.util.Date;

import static org.libre.lingvo.model.ParameterNames.*;


/**
 * Created by igorek2312 on 08.09.16.
 */

@Configuration
public class UserCriteriaQueries extends AbstractCriteriaQueriesConfig {

    @Bean
    public CriteriaQuery<User> findUserByEmail() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        ParameterExpression<String> parameter = cb.parameter(String.class, "templates");
        cq.where(cb.equal(userRoot.get(User_.email), parameter));

        return cq;
    }

    @Bean
    public CriteriaQuery<User> findUsersByEmailSubstring() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        ParameterExpression<String> parameter = cb.parameter(String.class, "emailSubstring");
        Expression<String> pattern = cb.concat(cb.concat("%", parameter), "%");
        cq.where(cb.like(userRoot.get(User_.email), pattern));
        return cq;
    }

    @Bean
    public CriteriaQuery<User> findExpiredNotActivatedUsers() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        ParameterExpression<Date> expiredCurrentDateParameter = cb.parameter(Date.class, EXPIRED_CURRENT_DATE);
        Path<Date> registrationDatePath = userRoot.get(User_.registrationDate);
        Path<Boolean> enabledPath = userRoot.get(User_.enabled);
        cq.select(userRoot);

        cq.where(
                cb.and(
                        cb.greaterThanOrEqualTo(expiredCurrentDateParameter, registrationDatePath),
                        cb.equal(enabledPath, false)
                )
        );

        return cq;
    }

    @Bean
    public CriteriaQuery<Boolean> existsUserWithEmail() {
        CriteriaQuery<Boolean> cq = cb.createQuery(Boolean.class);
        cq.from(User.class);
        ParameterExpression<String> emailParameter = cb.parameter(String.class, EMAIL);
        Subquery<User> sq = cq.subquery(User.class);
        Root<User> userRoot = sq.from(User.class);
        sq.select(userRoot);
        sq.where(cb.equal(userRoot.get(User_.email), emailParameter));
        CriteriaBuilder.Case<Boolean> booleanCase = cb.<Boolean>selectCase();
        Expression<Boolean> booleanExpression = booleanCase.when(cb.exists(sq), true).otherwise(false);
        cq.select(booleanExpression);
        return cq;
    }

    @Bean
    public CriteriaQuery<User> findUsers() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.select(userRoot);
        return cq;
    }

    @Bean
    public CriteriaQuery<User> findByActivationKey() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        ParameterExpression<String> activationKeyPrm = cb.parameter(String.class, ACTIVATION_KEY);
        Path<String> activationKeyPath = userRoot.get(User_.activationKey);
        cq.select(userRoot);
        cq.where(cb.equal(activationKeyPath, activationKeyPrm));
        return cq;
    }

    @Bean
    public CriteriaQuery<User> findByResetKey() {
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        ParameterExpression<String> resetKeyPrm = cb.parameter(String.class, RESET_KEY);
        Path<String> resetKeyPath = userRoot.get(User_.resetKey);
        cq.select(userRoot);
        cq.where(cb.equal(resetKeyPath, resetKeyPrm));
        return cq;
    }
}
