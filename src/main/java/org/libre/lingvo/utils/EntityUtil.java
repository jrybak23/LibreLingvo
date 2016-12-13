package org.libre.lingvo.utils;

import org.libre.lingvo.dao.GenericDao;
import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;

/**
 * Created by igorek2312 on 03.11.16.
 */
public class EntityUtil {

    public static <T, ID> T findOrThrowNotFound(GenericDao<T, ID> dao, ID id) {
        return dao.find(id).orElseThrow(() -> {
            CustomError error = CustomError.NO_ENTITY_WITH_SUCH_ID;
            error.setDescriptionArgs(dao.getDaoType().getName(), id);
            return new CustomErrorException(error);
        });
    }

    public static <T, ID> T getOneOrThrowNotFound(GenericDao<T, ID> dao, ID id) {
        return dao.getOne(id).orElseThrow(() -> {
            CustomError error = CustomError.NO_ENTITY_WITH_SUCH_ID;
            error.setDescriptionArgs(dao.getDaoType().getName(), id);
            return new CustomErrorException(error);
        });
    }

}
