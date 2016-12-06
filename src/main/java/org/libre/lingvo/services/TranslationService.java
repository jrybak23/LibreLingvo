package org.libre.lingvo.services;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;

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

            Boolean learned, List<Long> tagIds, SortingOptions sortOrder, TranslationSortFieldOptions sortField
    );

    TranslationDetailDto getUserTranslationDetailDto(Long userId, Long translationId);

    TranslationNoteDto getUserTranslationNote(Long userId, Long translationId);

    void updateTranslation(User user, Long translationId, InputTranslationDto dto);
    void updateTranslationNote(User user, Long translationId, TranslationNoteDto dto);

    void deleteUserTranslation(Long userId, Long translationId);

    void deleteUserTranslations(User user, List<Long> ids);

    List<TagDto> getTranslationTags(long userId,long translationId);
}
