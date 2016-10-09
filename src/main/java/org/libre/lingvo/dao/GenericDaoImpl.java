package org.libre.lingvo.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 08.09.16.
 */

public class GenericDaoImpl <T, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<? extends T> daoType;

    /**
     * By defining this class as abstract, we prevent Spring from creating instance of this class
     * If not defined abstract getClass().getGenericSuperClass() would return Object.
     * There would be exception because Object class does not hava constructor with parameters.
     */
    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from "+daoType.getName()).getResultList();
    }

    @Override
    public Optional<T> find(ID id) {
        return Optional.ofNullable(entityManager.find(daoType,id));
    }

    @Override
    public Optional<T> getOne(ID id) {
        return Optional.ofNullable(entityManager.getReference(daoType,id));
    }

    @Override
    public List<T> findByIds(List<ID> ids) {
        Query query = entityManager.createQuery("SELECT e FROM "+daoType.getName()+" e WHERE e.id in :ids");
        query.setParameter("ids", ids);
        return query.getResultList();
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(ID id) {
        getOne(id).ifPresent(entityManager::remove);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}