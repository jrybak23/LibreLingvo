package org.libre.lingvo.utils.optional.dao;

import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Created by igorek2312 on 14.10.16.
 */
public class OptionalDaoUtil {
    public static <T> Optional<T> findOptional(final DaoRetriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            //log
        }
        return Optional.empty();
    }
}
