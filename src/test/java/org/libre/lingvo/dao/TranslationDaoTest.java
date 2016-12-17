package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Word;
import org.libre.lingvo.reference.PartOfSpeech;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
                .hasSize(3)
                .extracting(Translation::getSourceWord)
                .extracting(Word::getLangCode)
                .containsOnly("en");

        assertThat(translations)
                .extracting(Translation::getResultWord)
                .extracting(Word::getLangCode)
                .containsOnly("uk");

        assertThat(translations)
                .extracting(Translation::getSourceWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("sample", "code", "butterfly");

        assertThat(translations)
                .extracting(Translation::getResultWord)
                .extracting(Word::getText)
                .containsExactlyInAnyOrder("зразок", "код", "метелик");
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
}
