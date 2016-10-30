package org.libre.lingvo.services;

import org.libre.lingvo.dto.AddedTranslationDto;
import org.libre.lingvo.dto.TranslationDto;

import java.util.List;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface TranslationService {
    void addUserTranslation(Long userId, AddedTranslationDto dto);

    List<TranslationDto> checkForUserTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    );

    List<TranslationDto> getUserTranslations(Long userId, Integer pageIndex, Integer maxRecords, String searchSubstring);
}
