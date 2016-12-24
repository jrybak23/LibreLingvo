package org.libre.lingvo.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface GenericDao<T,ID> {
    List<T> findAll();

    Optional<T> find(ID id);

    Optional<T> getOne(ID id);

    List<T> getByIds(List<ID> ids);

    void create(T entity);

    void update(T entity);

    void deleteById(ID id);

    void delete(T entity);

    Class getDaoType();

    void flush();
}
