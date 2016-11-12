package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.TranslationDetailDto;
import org.libre.lingvo.dto.TranslationLearningDto;
import org.libre.lingvo.dto.TranslationListItemDto;
import org.libre.lingvo.entities.Translation;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Component
public class TranslationDtoConverterImpl extends AbstractDtoConverter implements TranslationDtoConverter {

    @Override
    public TranslationListItemDto convertToTranslationDto(Translation translation) {
        return modelMapper.map(translation, TranslationListItemDto.class);
    }

    @Override
    public TranslationDetailDto convertToTranslationDetailDto(Translation translation) {
        return modelMapper.map(translation, TranslationDetailDto.class);
    }

    @Override
    public TranslationLearningDto convertToTranslationLearningDto(Translation translation) {
        return modelMapper.map(translation,TranslationLearningDto.class);
    }
}
