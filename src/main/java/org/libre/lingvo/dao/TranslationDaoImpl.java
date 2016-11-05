package org.libre.lingvo.dao;

import com.google.common.cache.LoadingCache;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

import static org.libre.lingvo.model.ParameterNames.*;
import static org.libre.lingvo.utils.optional.dao.OptionalDaoUtil.findOptional;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Repository
public class TranslationDaoImpl extends GenericDaoImpl<Translation, Long> implements TranslationDao {
    @Autowired
    private LoadingCache<TranslationsCriteria, CriteriaQuery> filteredTranslationsQueries;

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
    private CriteriaQuery<PartOfSpeech>getPartsOfSpeechByUserId;

    @Override
    public List<Translation> findFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            TranslationSortFieldOptions sortFieldOption,
            SortingOptions sortingOption,
            Integer pageIndex,
            Integer maxRecords
    ) {
        CriteriaQuery<Translation> query = filteredTranslationsQueries.getUnchecked(new TranslationsCriteria(
                ActionOptions.FIND,
                sortFieldOption,
                sortingOption
        ));
        return entityManager.createQuery(query)
                .setParameter(USER_ID, userId)
                .setParameter(SEARCH_SUBSTRING, searchSubstring)
                .setParameter(PART_OF_SPEECH, partOfSpeech)
                .setParameter(SOURCE_LANG_KEY, sourceLangCode)
                .setParameter(RESULT_LANG_KEY, resultLangCode)
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
            String resultLangCode
    ) {
        CriteriaQuery<Long> query = filteredTranslationsQueries.getUnchecked(new TranslationsCriteria(
                ActionOptions.COUNT,
                null,
                null
        ));
        return entityManager.createQuery(query)
                .setParameter(USER_ID, userId)
                .setParameter(SEARCH_SUBSTRING, searchSubstring)
                .setParameter(PART_OF_SPEECH, partOfSpeech)
                .setParameter(SOURCE_LANG_KEY, sourceLangCode)
                .setParameter(RESULT_LANG_KEY, resultLangCode)
                .getSingleResult();
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
                .setParameter(SOURCE_LANG_KEY, sourceLangKey)
                .setParameter(RESULT_TEXT, resultText)
                .setParameter(RESULT_LANG_KEY, resultLangKey)
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
                .setParameter(SOURCE_LANG_KEY, sourceLangKey)
                .setParameter(RESULT_LANG_KEY, resultLangKey)
                .getResultList();
    }

    @Override
    public Optional<Boolean> existsOtherTranslationsDependedOnWord(Long translationId, Long wordId) {
        return findOptional(() -> entityManager.createQuery(existsOtherTranslationsDependedOnWord)
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
