package org.libre.lingvo.services;

import org.libre.lingvo.dao.TranslationDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dao.WordDao;
import org.libre.lingvo.dto.*;
import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.Word;
import org.libre.lingvo.model.PartOfSpeech;
import org.libre.lingvo.utils.dto.converters.TranslationDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by igorek2312 on 29.10.16.
 */
@Service
@Transactional
public class TranslationServiceImpl implements TranslationService {
    @Autowired
    private TranslationDao translationDao;

    @Autowired
    private WordDao wordDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    TranslationDtoConverter translationDtoConverter;


    private Supplier<Word> getWordSupplier(String text, String langKey) {
        return () -> {
            Word word = new Word();
            word.setText(text);
            word.setLangKey(langKey);
            wordDao.create(word);
            return word;
        };
    }

    @Override
    public CreatedResourceDto addUserTranslation(Long userId, AddedTranslationDto dto) {
        Translation translation = new Translation();
        User user = userDao.find(userId)
                .orElseThrow(() -> {
                    CustomError error = CustomError.NO_ENTITY_WITH_SUCH_ID;
                    error.setDescriptionArgs(User.class.getName(), userId);
                    return new CustomErrorException(error);
                });
        translation.setUser(user);

        String sourceText = dto.getSourceText().trim();
        String resultText = dto.getResultText().trim();
        String sourceLangKey = dto.getSourceLangKey();
        String resultLangKey = dto.getResultLangKey();

        Boolean exists = translationDao.existsSuchTranslation(
                userId,
                sourceText,
                sourceLangKey,
                resultText,
                resultLangKey,
                dto.getPartOfSpeech()
        ).orElse(false);
        if (exists)
            throw new CustomErrorException(CustomError.USER_HAS_ALREADY_SUCH_TRANSLATION);


        Word sourceWord = wordDao.findByTextAndLangKey(sourceText, sourceLangKey)
                .orElseGet(getWordSupplier(sourceText, sourceLangKey));

        Word resultWord = wordDao.findByTextAndLangKey(resultText, resultLangKey)
                .orElseGet(getWordSupplier(resultText, resultLangKey));

        translation.setSourceWord(sourceWord);
        translation.setResultWord(resultWord);
        translation.setPartOfSpeech(dto.getPartOfSpeech());
        translation.setNote(dto.getNote());
        translation.setLastModificationDate(Date.from(Instant.now()));
        translation.setFolder(user.getRootFolder());

        translationDao.create(translation);
        return new CreatedResourceDto(translation.getId());
    }

    @Override
    public TranslationsDto checkForUserTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    ) {
        List<TranslationDto> translations = translationDao.findSuchTranslations(
                userId,
                sourceText.trim(),
                sourceLangKey,
                resultLangKey
        )
                .stream()
                .map(translation -> {
                    translation.incrementViews();
                    return translationDtoConverter.convertToTranslationDto(translation);
                })
                .collect(Collectors.toList());

        TranslationsDto dto = new TranslationsDto();
        dto.setTranslations(translations);

        return dto;
    }

    @Override
    public TranslationsDto getUserTranslations(
            Long userId,
            Integer pageIndex,
            Integer maxRecords,
            String searchSubstring,
            PartOfSpeech partOfSpeech
    ) {
        List<TranslationDto> translations = translationDao.findFilteredUserTranslations(
                userId,
                searchSubstring,
                partOfSpeech,
                pageIndex,
                maxRecords
        )
                .stream()
                .map(translationDtoConverter::convertToTranslationDto)
                .collect(Collectors.toList());
        Long filteredRecords = translationDao.countFilteredUserTranslations(
                userId,
                searchSubstring,
                partOfSpeech
        );
        Long totalRecords = translationDao.countTotalUserTranslations(userId);

        TranslationsDto dto = new TranslationsDto();
        dto.setTranslations(translations);
        dto.setFilteredRecords(filteredRecords);
        dto.setTotalRecords(totalRecords);
        return dto;
    }

    @Override
    public TranslationDetailDto getUserTranslationDetailDto(Long userId, Long translationId) {
        Translation translation = translationDao.find(translationId).orElseThrow(() -> {
            CustomError error = CustomError.NO_ENTITY_WITH_SUCH_ID;
            error.setDescriptionArgs(Translation.class.getName(), translationId);
            return new CustomErrorException(error);
        });

        if (!translation.getUser().getId().equals(userId))
            throw new CustomErrorException(CustomError.FORBIDDEN);

        return translationDtoConverter.convertToTranslationDetailDto(translation);
    }

    private void safeDelete(Long translationId, Word word) {
        Boolean exists = translationDao.existsOtherTranslationsDependedOnWord(translationId, word.getId())
                .orElse(false);
        if (!exists)
            wordDao.delete(word);
    }

    @Override
    public void deleteUserTranslation(Long userId, Long translationId) {
        Translation translation = translationDao.find(translationId).orElseThrow(() -> {
            CustomError error = CustomError.NO_ENTITY_WITH_SUCH_ID;
            error.setDescriptionArgs(Translation.class.getName(), translationId);
            return new CustomErrorException(error);
        });

        if (!translation.getUser().getId().equals(userId))
            throw new CustomErrorException(CustomError.FORBIDDEN);

        safeDelete(translationId, translation.getSourceWord());
        safeDelete(translationId, translation.getResultWord());

        translationDao.delete(translation);
    }
}
