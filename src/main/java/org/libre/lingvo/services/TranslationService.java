package org.libre.lingvo.services;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.model.PartOfSpeech;
import org.libre.lingvo.model.SortingOptions;
import org.libre.lingvo.model.TranslationSortFieldOptions;

import java.util.List;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface TranslationService {
    CreatedResourceDto addUserTranslation(Long userId, InputTranslationDto dto);

    TranslationsDto checkForUserTranslations(
            Long userId,
            String sourceText,
            String sourceLangCode,
            String resultLangCode
    );

    TranslationsDto getUserTranslations(
            Long userId,
            Integer pageIndex,
            Integer maxRecords,
            String searchSubstring,
            PartOfSpeech partOfSpeech,
            String sourceLangCode,
            String resultLangCode,
            TranslationSortFieldOptions sortField,
            SortingOptions sortOrder
    );

    TranslationDetailDto getUserTranslationDetailDto(Long userId, Long translationId);

    TranslationNoteDto getUserTranslationNote(Long userId, Long translationId);

    void updateTranslation(Long userId, Long translationId, InputTranslationDto dto);
    void updateTranslationNote(Long userId, Long translationId, TranslationNoteDto dto);

    void deleteUserTranslation(Long userId, Long translationId);

    void deleteUserTranslations(Long userId, List<Long> ids);
}
