package org.libre.lingvo.dao;

import org.libre.lingvo.dao.criteria.queries.TranslationFilterQueryBuilder;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

import static org.libre.lingvo.reference.ParameterNames.*;
import static org.libre.lingvo.utils.DaoRetrieverUtil.exists;
import static org.libre.lingvo.utils.DaoRetrieverUtil.findOptional;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class TranslationDaoImpl extends GenericDaoImpl<Translation, Long> implements TranslationDao {

    @Autowired
    @Qualifier("countTotalUserTranslations")
    private CriteriaQuery<Long> countTotalUserTranslations;

    @Autowired
    @Qualifier("existsSuchTranslation")
    private CriteriaQuery<Boolean> existsSuchTranslation;

    @Autowired
    @Qualifier("findUserTranslationsForChecking")
    private CriteriaQuery<Translation> findUserTranslationsForChecking;

    @Autowired
    @Qualifier("existsOtherTranslationsDependedOnWord")
    private CriteriaQuery<Boolean> existsOtherTranslationsDependedOnWord;

    @Autowired
    @Qualifier("getLangKeysByUserId")
    private CriteriaQuery<Tuple> getLangKeysByUserId;

    @Autowired
    @Qualifier("getPartsOfSpeechByUserId")
    private CriteriaQuery<PartOfSpeech> getPartsOfSpeechByUserId;

    @Autowired
    private TranslationFilterQueryBuilder translationFilterQueryBuilder;

    @Override
    public List<Translation> findFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned,
            List<Long> tagIds,
            SortingOptions sortingOption,
            TranslationSortFieldOptions sortFieldOption, Integer pageIndex,
            Integer maxRecords
    ) {
        CriteriaQuery<Translation> cq = translationFilterQueryBuilder
                .setUserId(userId)
                .setSearchSubstring(searchSubstring)
                .setPartOfSpeech(partOfSpeech)
                .setSourceLangKey(sourceLangCode)
                .setResultLangKey(resultLangCode)
                .setLearned(learned)
                .setTagIds(tagIds)
                .setSortFieldOptions(sortFieldOption)
                .setSortingOptions(sortingOption)
                .build(Translation.class);

        return entityManager.createQuery(cq)
                .setFirstResult((pageIndex - 1) * maxRecords)
                .setMaxResults(maxRecords)
                .getResultList();
    }

    @Override
    public Long countFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned,
            List<Long> tagIds) {

        CriteriaQuery<Long> cq = translationFilterQueryBuilder
                .setUserId(userId)
                .setSearchSubstring(searchSubstring)
                .setPartOfSpeech(partOfSpeech)
                .setSourceLangKey(sourceLangCode)
                .setResultLangKey(resultLangCode)
                .setLearned(learned)
                .setTagIds(tagIds)
                .build(Long.class);

        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public Long countTotalUserTranslations(Long userId) {
        return entityManager.createQuery(countTotalUserTranslations)
                .setParameter(USER_ID, userId)
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
                .setParameter(USER_ID, userId)
                .setParameter(SOURCE_TEXT, sourceText)
                .setParameter(SOURCE_LANG_CODE, sourceLangKey)
                .setParameter(RESULT_TEXT, resultText)
                .setParameter(RESULT_LANG_CODE, resultLangKey)
                .setParameter(PART_OF_SPEECH, partOfSpeech)
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
                .setParameter(USER_ID, userId)
                .setParameter(SOURCE_TEXT, sourceText)
                .setParameter(SOURCE_LANG_CODE, sourceLangKey)
                .setParameter(RESULT_LANG_CODE, resultLangKey)
                .getResultList();
    }

    @Override
    public boolean existsOtherTranslationsDependedOnWord(Long translationId, Long wordId) {
        return exists(() -> entityManager.createQuery(existsOtherTranslationsDependedOnWord)
                .setParameter(TRANSLATION_ID, translationId)
                .setParameter(WORD_ID, wordId)
                .getSingleResult());
    }

    @Override
    public List<Tuple> getLangKeysByUserId(Long userId) {
        return entityManager.createQuery(getLangKeysByUserId)
                .setParameter(USER_ID, userId)
                .getResultList();
    }

    @Override
    public List<PartOfSpeech> getPartsOfSpeechByUserId(Long userId) {
        return entityManager.createQuery(getPartsOfSpeechByUserId)
                .setParameter(USER_ID, userId)
                .getResultList();
    }

}
