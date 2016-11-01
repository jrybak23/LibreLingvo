package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.AddedTranslationDto;
import org.libre.lingvo.dto.TranslationDetailDto;
import org.libre.lingvo.dto.TranslationDto;
import org.libre.lingvo.entities.Translation;

/**
 * Created by igorek2312 on 29.10.16.
 */
public interface TranslationDtoConverter {
    Translation convertFromAddedTranslationDto(AddedTranslationDto dto);

    TranslationDto convertToTranslationDto(Translation translation);

    TranslationDetailDto convertToTranslationDetailDto(Translation translation);
}
