package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.libre.lingvo.dto.LangCodesPairDto;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Word;
import org.libre.lingvo.reference.PartOfSpeech;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by igorek2312 on 17.12.16.
 */
public class TranslationDaoTest extends AbstractDbTest {
    private final static String SAMPLE_DATA_PATH = "/dao/user/translations-test-case.xml";

    @Autowired
    private TranslationDao translationDao;

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findFilteredUserTranslationsBySubstring() {
        List<Translation> translations = translationDao.findFilteredUserTranslations(
                1L,
                "e",
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                1,
                20
        );

        assertThat(translations)
                .hasSize(4)
                .extracting(Translation::getSourceWord)
                .extracting(Word::getLangCode)
                .containsOnly("en");

        assertThat(translations)
                .extracting(Translation::getResultWord)
                .extracting(Word::getLangCode)
                .containsOnly("uk", "ru");

        assertThat(translations)
                .extracting(Translation::getSourceWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("sample", "sample", "code", "butterfly");

        assertThat(translations)
                .extracting(Translation::getResultWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("зразок", "пример", "код", "метелик");
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findFilteredUserTranslationsByPartOfSpeech() {
        List<Translation> translations = translationDao.findFilteredUserTranslations(
                1L,
                "",
                PartOfSpeech.NOUN,
                null,
                null,
                false,
                null,
                null,
                null,
                1,
                20
        );

        assertThat(translations)
                .extracting(Translation::getSourceWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("sample", "code");

        assertThat(translations)
                .extracting(Translation::getResultWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("зразок", "код");
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findSuchTranslations() {
        List<Translation> translations = translationDao.findSuchTranslations(
                1L,
                "sample",
                "en",
                "uk"
        );

        assertThat(translations).hasSize(1);

        Translation translation = translations.get(0);
        assertEquals(translation.getSourceWord().getText(), "sample");
        assertEquals(translation.getResultWord().getText(), "зразок");
        assertEquals(translation.getSourceWord().getLangCode(), "en");
        assertEquals(translation.getResultWord().getLangCode(), "uk");
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void countFilteredUserTranslations() {
        long count = translationDao.countFilteredUserTranslations(
                1L,
                "",
                PartOfSpeech.NOUN,
                null,
                null,
                false,
                null
        );

        assertEquals(count, 2L);
    }


    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void countTotalUserTranslations() {
        long count = translationDao.countTotalUserTranslations(1L);
        assertEquals(count, 5);
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void existsSuchTranslation() {
        assertTrue(translationDao.existsSuchTranslation(
                1L,
                "sample",
                "en",
                "зразок",
                "uk",
                PartOfSpeech.NOUN
        ));
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void notExistsSuchTranslation() {
        assertFalse(translationDao.existsSuchTranslation(
                1L,
                "assertive",
                "en",
                "наполеглевий",
                "uk",
                PartOfSpeech.ADJECTIVE
        ));
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void existsOtherTranslationsDependedOnWord() {
        assertTrue(translationDao.existsOtherTranslationsDependedOnWord(1L, 1L));
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void notExistsOtherTranslationsDependedOnWord() {
        assertFalse(translationDao.existsOtherTranslationsDependedOnWord(2L, 3L));
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findLangCodesByUserId() {
        List<LangCodesPairDto> langCodesPairs = translationDao.findLangCodesByUserId(1L);
        assertThat(langCodesPairs)
                .hasSize(2)
                .extracting(LangCodesPairDto::getSource)
                .containsOnly("en");

        assertThat(langCodesPairs)
                .extracting(LangCodesPairDto::getResult)
                .containsOnly("uk", "ru");
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findPartsOfSpeechByUserId() {
        assertThat(translationDao.findPartsOfSpeechByUserId(1L))
                .containsExactlyInAnyOrder(PartOfSpeech.NOUN, PartOfSpeech.NOT_DEFINED);
    }

}
