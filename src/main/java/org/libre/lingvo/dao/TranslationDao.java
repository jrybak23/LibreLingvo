package org.libre.lingvo.dao;

import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.model.PartOfSpeech;
import org.libre.lingvo.model.SortingOptions;
import org.libre.lingvo.model.TranslationSortFieldOptions;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface TranslationDao extends GenericDao<Translation, Long> {
    List<Translation> findFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned, TranslationSortFieldOptions sortFieldOption,
            SortingOptions sortingOption,
            Integer pageIndex,
            Integer maxRecords
    );

    Long countFilteredUserTranslations(
            Long userId,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            Boolean learned);

    Long countTotalUserTranslations(Long userId);

    Optional<Boolean> existsSuchTranslation(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultText,
            String resultLangKey,
            PartOfSpeech partOfSpeech
    );

    List<Translation> findSuchTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    );

    Optional<Boolean> existsOtherTranslationsDependedOnWord(Long translationId, Long wordId);

    List<Tuple> getLangKeysByUserId(Long userId);

    List<PartOfSpeech> getPartsOfSpeechByUserId(Long userId);
}
