package org.libre.lingvo.utils.optional.dao;

import javax.persistence.NoResultException;

/**
 * Created by igorek2312 on 14.10.16.
 */
@FunctionalInterface
public interface DaoRetriever<T> {
    T retrieve() throws NoResultException;
}
