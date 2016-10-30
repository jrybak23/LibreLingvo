package org.libre.lingvo.services;

import org.libre.lingvo.dao.TranslationDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dao.WordDao;
import org.libre.lingvo.dto.AddedTranslationDto;
import org.libre.lingvo.dto.TranslationDto;
import org.libre.lingvo.dto.exception.CustomError;
import org.libre.lingvo.dto.exception.CustomErrorException;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.Word;
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
    public void addUserTranslation(Long userId, AddedTranslationDto dto) {

        Translation translation = new Translation();
        User user = userDao.find(userId)
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_ID));
        translation.setUser(user);

        String sourceText = dto.getSourceText();
        String resultText = dto.getResultText();
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
    }

    @Override
    public List<TranslationDto> checkForUserTranslations(
            Long userId,
            String sourceText,
            String sourceLangKey,
            String resultLangKey
    ) {
        return translationDao.findSuchTranslations(
                userId,
                sourceText,
                sourceLangKey,
                resultLangKey
        )
                .stream()
                .map(translationDtoConverter::convertToTranslationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TranslationDto> getUserTranslations(
            Long userId,
            Integer pageIndex,
            Integer maxRecords,
            String searchSubstring
    ) {
        return translationDao.findUserTranslations(userId, pageIndex, maxRecords)
                .stream()
                .map(translationDtoConverter::convertToTranslationDto)
                .collect(Collectors.toList());
    }
}
