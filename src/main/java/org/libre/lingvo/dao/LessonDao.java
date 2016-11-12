package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Lesson;

import java.util.List;

/**
 * Created by igorek2312 on 08.11.16.
 */
public interface LessonDao extends GenericDao<Lesson,Long> {
    List<Lesson> findByUserId(Long userId);

}
