package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by igorek2312 on 03.12.16.
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {

    @Override
    public List<Tag> findByUserId(long userId) {
        return getSession().createQuery("from org.libre.lingvo.entities.Tag tag where " +
                "tag.user.id = :userId " +
                "order by tag.position")
                .setParameter("userId", userId)
                .list();
    }

    @Override
    public void removeTranslationTag(long tagId, long translationId) {
        getSession().createQuery("delete from org.libre.lingvo.entities.TranslationTag translationTag where " +
                "translationTag.pk.translation.id = :translationId and " +
                "translationTag.pk.tag.id = :tagId")
                .setParameter("translationId", translationId)
                .setParameter("tagId", tagId)
                .executeUpdate();
    }
}
