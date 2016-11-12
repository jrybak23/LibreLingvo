package org.libre.lingvo.services;

import org.libre.lingvo.dao.LessonDao;
import org.libre.lingvo.dao.TranslationDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dto.CreatedResourceDto;
import org.libre.lingvo.dto.LessonDto;
import org.libre.lingvo.dto.LessonItemDto;
import org.libre.lingvo.dto.TranslationLearningDto;
import org.libre.lingvo.entities.Lesson;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.utils.dto.converters.TranslationDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.libre.lingvo.utils.EntityUtil.findOrThrowNotFound;

/**
 * Created by igorek2312 on 09.11.16.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonDao lessonDao;

    @Autowired
    private TranslationDao translationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TranslationDtoConverter translationDtoConverter;

    public void checkIfUserIsOwnerOfLesson(Long userId, Long lessonId) {
    }

    @Override
    public CreatedResourceDto createLesson(Long userId, List<Long> translationsIds) {
        User user = findOrThrowNotFound(userDao, userId);
        List<Translation> translations = translationDao.getByIds(translationsIds);
        Lesson lesson = new Lesson();


        lesson.setCompletedPartsOfLesson(0);
        lesson.setMaxPartsOfLesson(user.getLessonPartsCount());
        lesson.setWaitUnitNextLessonPart(new Date());

        translations.forEach(translation -> translation.setLesson(lesson));
        lesson.getTranslations().addAll(translations);

        lessonDao.create(lesson);

        return new CreatedResourceDto(lesson.getId());
    }

    @Override
    public List<LessonItemDto> getUserLessons(Long userId) {
        return lessonDao.findByUserId(userId)
                .stream()
                .map(lesson -> {
                    LessonItemDto dto = new LessonItemDto();
                    dto.setId(lesson.getId());
                    dto.setWaitUnitNextLessonPart(lesson.getWaitUnitNextLessonPart());
                    dto.setTitle("foo");
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public LessonDto getLesson(Long userId, Long lessonId) {
        Lesson lesson = findOrThrowNotFound(lessonDao, lessonId);
        checkIfUserIsOwnerOfLesson(userId, lessonId);

        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lessonId);
        lessonDto.setMaxPartsOfLesson(lesson.getMaxPartsOfLesson());
        lessonDto.setCompletedPartsOfLesson(lesson.getCompletedPartsOfLesson());
        List<TranslationLearningDto> translationDtos = lesson.getTranslations().stream()
                .map(translationDtoConverter::convertToTranslationLearningDto)
                .collect(Collectors.toList());
        lessonDto.setTranslations(translationDtos);

        return lessonDto;
    }

    @Override
    public void goNextPartOfLesson(Long userId, Long lessonId) {
        User user = findOrThrowNotFound(userDao, userId);
        Lesson lesson = findOrThrowNotFound(lessonDao, lessonId);
        checkIfUserIsOwnerOfLesson(userId, lessonId);

        lesson.increaseCompletedPartsOfLesson();

        if (lesson.isCompleted())
            lesson.getTranslations()
                    .forEach(trn -> {
                        trn.setLearned(true);
                        trn.setLesson(null);
                    });
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            calendar.add(Calendar.MINUTE, user.getMinutesBetweenLessonParts());
            lesson.setWaitUnitNextLessonPart(new Date(calendar.getTime().getTime()));
        }
        lessonDao.update(lesson);

        if (lesson.isCompleted())
            lessonDao.delete(lesson);
    }

    @Override
    public void deleteLesson(Long userId, Long lessonId) {
        Lesson lesson = findOrThrowNotFound(lessonDao, lessonId);
        checkIfUserIsOwnerOfLesson(userId, lessonId);
        lesson.getTranslations().forEach(trn -> {
            trn.setLesson(null);
            translationDao.update(trn);
        });
        lessonDao.delete(lesson);
    }
}
