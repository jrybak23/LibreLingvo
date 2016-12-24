package org.libre.lingvo.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 08.09.16.
 */

public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

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
        return getSession().createQuery("from " + daoType.getName()).list();
    }

    @Override
    public Optional<T> find(ID id) {
        return Optional.ofNullable(getSession().get(daoType, id));
    }

    @Override
    public Optional<T> getOne(ID id) {
        return Optional.ofNullable(getSession().load(daoType, id));
    }

    @Override
    public List<T> getByIds(List<ID> ids) {
        Query query = getSession().createQuery("SELECT e FROM " + daoType.getName() + " e WHERE e.id in :ids");
        query.setParameter("ids", ids);
        return query.list();
    }

    @Override
    public void create(T entity) {
        getSession().persist(entity);
    }

    @Override
    public void update(T entity) {
        getSession().merge(entity);
    }

    @Override
    public void deleteById(ID id) {
        getOne(id).ifPresent(getSession()::delete);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public Class getDaoType() {
        return daoType;
    }
}