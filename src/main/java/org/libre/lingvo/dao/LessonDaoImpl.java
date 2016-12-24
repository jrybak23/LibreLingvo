package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Lesson;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by igorek2312 on 08.11.16.
 */
@Repository
public class LessonDaoImpl extends GenericDaoImpl<Lesson, Long> implements LessonDao {
    @Override
    public List<Lesson> findByUserId(Long userId) {
        return getSession().createQuery("select distinct lesson from org.libre.lingvo.entities.Lesson lesson " +
                "inner join lesson.translations translation where translation.user.id = :userId")
                .setParameter("userId", userId)
                .list();
    }
}
