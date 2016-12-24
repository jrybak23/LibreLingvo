package org.libre.lingvo.dao.criteria.queries;

import org.hibernate.Session;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by igorek2312 on 16.10.16.
 */
public abstract class AbstractCriteriaQueriesConfig {
    @PersistenceContext
    protected EntityManager entityManager;

    protected CriteriaBuilder cb;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @PostConstruct
    private void postConstruct() {
        cb = entityManager.getCriteriaBuilder();
    }
}
