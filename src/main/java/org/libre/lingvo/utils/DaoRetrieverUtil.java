package org.libre.lingvo.utils;

import org.libre.lingvo.utils.DaoRetriever;

import javax.persistence.NoResultException;
import java.util.Optional;

/**
 * Created by igorek2312 on 14.10.16.
 */
public class DaoRetrieverUtil {
    public static <T> Optional<T> findOptional(final DaoRetriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException ex) {
            //log
        }
        return Optional.empty();
    }

    public static boolean exists(final DaoRetriever<Boolean> retriever) {
        try {
            return retriever.retrieve();
        } catch (NoResultException ex) {
            //log
        }
        return false;
    }
}
