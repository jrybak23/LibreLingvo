package org.libre.lingvo.utils.dto.converters;

import org.libre.lingvo.dto.TagDto;
import org.libre.lingvo.entities.Tag;

/**
 * Created by igorek2312 on 03.12.16.
 */
public interface TagDtoConverter {
    TagDto convertToTagDto(Tag tag);
}
