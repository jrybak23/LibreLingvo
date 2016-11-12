package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.libre.lingvo.model.ParameterNames.USER_ID;

/**
 * Created by igorek2312 on 08.11.16.
 */
@Repository
public class LessonDaoImpl extends GenericDaoImpl<Lesson,Long> implements LessonDao {
    @Autowired
    @Qualifier("findLessonsByUserId")
    private CriteriaQuery<Lesson> findLessonsByUserId;

    @Override
    public List<Lesson> findByUserId(Long userId) {
        return entityManager.createQuery(findLessonsByUserId)
                .setParameter(USER_ID,userId)
                .getResultList();
    }
}
