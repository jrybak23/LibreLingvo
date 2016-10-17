package org.libre.lingvo.config.criteria.queries;

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

    @PostConstruct
    private void postConstruct(){
        cb =entityManager.getCriteriaBuilder();
    }
}
