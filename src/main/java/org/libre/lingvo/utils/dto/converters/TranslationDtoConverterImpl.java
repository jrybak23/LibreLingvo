package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.InputTranslationDto;
import org.libre.lingvo.dto.TranslationDetailDto;
import org.libre.lingvo.dto.TranslationDto;
import org.libre.lingvo.entities.Translation;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Component
public class TranslationDtoConverterImpl extends AbstractDtoConverter implements TranslationDtoConverter {
    @Override
    public Translation convertFromAddedTranslationDto(InputTranslationDto dto) {
        return modelMapper.map(dto, Translation.class);
    }

    @Override
    public TranslationDto convertToTranslationDto(Translation translation) {
        return modelMapper.map(translation, TranslationDto.class);
    }

    @Override
    public TranslationDetailDto convertToTranslationDetailDto(Translation translation) {
        return modelMapper.map(translation, TranslationDetailDto.class);
    }
}
