package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.TranslationDetailDto;
import org.libre.lingvo.dto.TranslationLearningDto;
import org.libre.lingvo.dto.TranslationListItemDto;
import org.libre.lingvo.entities.Translation;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface TranslationDtoConverter {
    TranslationListItemDto convertToTranslationDto(Translation translation);

    TranslationDetailDto convertToTranslationDetailDto(Translation translation);

    TranslationLearningDto convertToTranslationLearningDto(Translation translation);
}
