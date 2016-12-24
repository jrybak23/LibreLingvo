package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Tag;

import java.util.List;

/**
 * Created by igorek2312 on 03.12.16.
 */
public interface TagDao extends GenericDao<Tag, Long> {
    List<Tag> findByUserId(long userId);

    void removeTranslationTag(long tagId, long translationId);
}
