package com.example.libre.lingvo.dao;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */
public interface GenericDao<T,ID> {
    List<T> findAll();

    T find(ID id);

    T getOne(ID id);

    List<T> findByIds(List<ID> ids);

    T create(T entity);

    void update(T entity);

    void delete(ID id);
}
