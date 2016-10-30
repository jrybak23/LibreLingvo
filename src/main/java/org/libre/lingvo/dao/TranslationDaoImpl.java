package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.model.PartOfSpeech;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

import static org.libre.lingvo.utils.optional.dao.OptionalDaoUtil.findOptional;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class TranslationDaoImpl extends GenericDaoImpl<Translation, Long> implements TranslationDao {
    @Autowired
    @Qualifier("findUserTranslations")
    private CriteriaQuery<Translation> findUserTranslations;

    @Autowired
    @Qualifier("countTranslationsByUserId")
    private CriteriaQuery<Long> countTranslationsByUserId;

    @Autowired
    @Qualifier("existsSuchTranslation")
    private CriteriaQuery<Boolean> existsSuchTranslation;

    @Autowired
    @Qualifier("findUserTranslationsForChecking")
    private CriteriaQuery<Translation> findUserTranslationsForChecking;

    @Override
    public List<Translation> findUserTranslations(
            Long userId,
            Integer pageIndex,
            Integer maxRecords
    ) {
        return entityManager.createQuery(findUserTranslations)
                .setParameter("userId", userId)
                .setFirstResult(pageIndex - 1)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public Long countByUserId(Long userId) {
        return entityManager.createQuery(countTranslationsByUserId)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public Optional<Boolean> existsSuchTranslation(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultText,
            String resultLangKey,
            PartOfSpeech partOfSpeech
    ) {
        return findOptional(() -> entityManager.createQuery(existsSuchTranslation)
                .setParameter("userId", userId)
                .setParameter("sourceText", sourceText)
                .setParameter("sourceLangKey", sourceLangKey)
                .setParameter("resultText", resultText)
                .setParameter("resultLangKey", resultLangKey)
                .setParameter("partOfSpeech", partOfSpeech)
                .getSingleResult());
    }

    @Override
    public List<Translation> findSuchTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    ) {
        return entityManager.createQuery(findUserTranslationsForChecking)
                .setParameter("userId", userId)
                .setParameter("sourceText", sourceText)
                .setParameter("sourceLangKey", sourceLangKey)
                .setParameter("resultLangKey", resultLangKey)
                .getResultList();
    }


}
