package org.libre.lingvo.services;

import org.libre.lingvo.dao.TagDao;
import org.libre.lingvo.dao.TranslationDao;
import org.libre.lingvo.dao.TranslationTagDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.TagDto;
import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.Tag;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.TranslationTag;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.utils.dto.converters.TagDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.libre.lingvo.utils.EntityUtil.findOrThrowNotFound;

/**
 * Created by igorek2312 on 03.12.16.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TranslationDao translationDao;

    @Autowired
    private TagDtoConverter tagDtoConverter;

    @Autowired
    private TranslationTagDao translationTagDao;

    @Override
    public List<TagDto> getUserTags(long userId) {
        return tagDao.findByUserId(userId).stream()
                .map(tagDtoConverter::convertToTagDto)
                .collect(Collectors.toList());
    }

    private void checkIfUserIsOwnerOfFolder(long userId, Tag tag) {
        if (!tag.getUser().getId().equals(userId))
            throw new CustomErrorException(CustomError.FORBIDDEN);
    }

    @Override
    public CreatedResourceDto createTag(long userId, TagDto dto) {
        User user = findOrThrowNotFound(userDao, userId);
        user.getTags().forEach(Tag::increasePosition);

        Tag tag = new Tag();
        tag.setUser(user);
        tag.setName(dto.getName());
        tag.setPosition(0);
        tagDao.create(tag);
        userDao.update(user);

        return new CreatedResourceDto(tag.getId());
    }

    @Override
    public void deleteTag(long userId, long tagId) {
        Tag tag = findOrThrowNotFound(tagDao, tagId);
        checkIfUserIsOwnerOfFolder(userId, tag);
        tag.getTranslationTags().forEach(
                translationTagDao::delete
        );
        tagDao.delete(tag);
    }

    private Optional<Tag> findById(long id, List<Tag> tags) {
        return tags.stream()
                .filter(folder -> folder.getId().equals(id))
                .findFirst();
    }

    @Override
    public void updateTagsPosition(long userId, List<TagDto> dtos) {
        List<Long> ids = dtos.stream()
                .map(TagDto::getId)
                .collect(Collectors.toList());

        List<Tag> tags = tagDao.getByIds(ids);

        tags.stream()
                .findFirst()
                .ifPresent(
                        tag -> checkIfUserIsOwnerOfFolder(userId, tag)
                );

        final int[] position = {0};
        ids.forEach(id -> findById(id, tags)
                .ifPresent(
                        tag -> {
                            tag.setPosition(position[0]++);
                            tagDao.update(tag);
                        }
                )
        );
    }

    @Override
    public void renameTag(long tagId, String name) {
        Tag tag = findOrThrowNotFound(tagDao, tagId);
        tag.setName(name);
        tagDao.update(tag);
    }

    @Override
    public void addTranslations(long tagId, List<Long> translationIds) {
        Tag tag = findOrThrowNotFound(tagDao, tagId);
        List<Translation> translations = translationDao.getByIds(translationIds);
        translations.forEach(translation -> {
            TranslationTag translationTag = new TranslationTag();
            translationTag.setTranslation(translation);
            translationTag.setTag(tag);
            if (!tag.getTranslationTags().contains(translationTag)) {
                translationTagDao.create(translationTag);
                tag.getTranslationTags().add(translationTag);
            }
        });
        tagDao.update(tag);
    }

    @Override
    public void removeTranslation(long tagId, long translationId) {
        tagDao.removeTranslation(tagId, translationId);
    }
}
