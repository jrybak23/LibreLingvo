package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Tag;
import org.libre.lingvo.entities.TranslationTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.libre.lingvo.reference.ParameterNames.*;

/**
 * Created by igorek2312 on 03.12.16.
 */
@Repository
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao {

    @Autowired
    @Qualifier(value = "findTagsByUserId")
    private CriteriaQuery<Tag> findTagsByUserId;

    @Autowired
    @Qualifier(value = "removeTranslationTag")
    private CriteriaDelete<TranslationTag> removeTranslationTag;

    @Override
    public List<Tag> findByUserId(long userId) {
        return entityManager.createQuery(findTagsByUserId)
                .setParameter(USER_ID, userId)
                .getResultList();
    }

    @Override
    public void removeTranslation(long tagId, long translationId) {
        entityManager.createQuery(removeTranslationTag)
                .setParameter(TRANSLATION_ID, translationId)
                .setParameter(TAG_ID, tagId)
                .executeUpdate();
    }
}
