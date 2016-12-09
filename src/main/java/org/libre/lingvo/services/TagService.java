package org.libre.lingvo.services;

import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.TagDto;

import java.util.List;

/**
 * Created by igorek2312 on 03.12.16.
 */
public interface TagService {
    List<TagDto> getUserTags(long userId);

    CreatedResourceDto createTag(long userId, TagDto dto);

    void deleteTag(long userId, long tagId);

    void updateTagsPosition(long userId, List<TagDto> dtos);

    void renameTag(long tagId, String name);

    void tagTranslations(long tagId, List<Long> translationIds);

    void removeTranslation(long tagId, long translationId);
}
