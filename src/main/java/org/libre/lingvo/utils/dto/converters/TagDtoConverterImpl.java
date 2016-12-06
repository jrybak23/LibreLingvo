package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.TagDto;
import org.libre.lingvo.entities.Tag;
import org.springframework.stereotype.Component;

/**
 * Created by igorek2312 on 03.12.16.
 */
@Component
public class TagDtoConverterImpl extends AbstractDtoConverter implements TagDtoConverter {

    @Override
    public TagDto convertToTagDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
