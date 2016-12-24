package org.libre.lingvo.service;

import org.junit.Before;
import org.junit.Test;
import org.libre.lingvo.dao.TranslationDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dao.WordDao;
import org.libre.lingvo.dto.TranslationListItemDto;
import org.libre.lingvo.dto.TranslationsDto;
import org.libre.lingvo.entities.Translation;
import org.libre.lingvo.entities.Word;
import org.libre.lingvo.reference.PartOfSpeech;
import org.libre.lingvo.reference.SortingOptions;
import org.libre.lingvo.reference.TranslationSortFieldOptions;
import org.libre.lingvo.services.TranslationService;
import org.libre.lingvo.services.TranslationServiceImpl;
import org.libre.lingvo.utils.dto.converters.TagDtoConverter;
import org.libre.lingvo.utils.dto.converters.TranslationDtoConverter;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by igorek2312 on 17.12.16.
 */
public class TranslationServiceTest extends AbstractServiceTest {
    public static final long USER_ID = 1L;

    @Mock
    private TranslationDao translationDao;

    @Mock
    private WordDao wordDao;

    @Mock
    private UserDao userDao;

    @Autowired
    private TranslationDtoConverter translationDtoConverter;

    @Autowired
    private TagDtoConverter tagDtoConverter;

    private TranslationService translationService;

    @Before
    public void setup() {
        translationService = new TranslationServiceImpl(translationDao, wordDao, userDao, translationDtoConverter, tagDtoConverter);
    }

    @Test
    public void getUserTranslations() {
        Word source = new Word();
        source.setText("sample");
        source.setLangCode("en");
        Word result = new Word();
        result.setText("зразок");
        result.setLangCode("uk");
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceWord(source);
        translation.setResultWord(result);
        List<Translation> translations = new ArrayList<Translation>();
        translations.add(translation);

        when(translationDao.findFilteredUserTranslations(
                USER_ID,
                "samp",
                PartOfSpeech.NOUN,
                "en",
                "uk",
                null,
                null,
                SortingOptions.DESC,
                TranslationSortFieldOptions.SORT_MODIFICATION_DATE,
                1,
                20
        ))
                .thenReturn(translations);

        when(translationDao.countFilteredUserTranslations(
                USER_ID,
                "samp",
                PartOfSpeech.NOUN,
                "en",
                "uk",
                null,
                null
        ))
                .thenReturn(1L);

        when(translationDao.countTotalUserTranslations(
                USER_ID
        ))
                .thenReturn(1L);

        TranslationsDto translationsDto = translationService.getUserTranslations(
                USER_ID,
                1,
                20,
                "samp",
                PartOfSpeech.NOUN,
                "en",
                "uk",
                null,
                null,
                null,
                null
        );

        List<TranslationListItemDto> translationsItems = translationsDto.getTranslations();
        assertThat(translationsItems).hasSize(1);
        TranslationListItemDto dto = translationsItems.get(0);
        assertEquals(dto.getSourceWord().getText(), "sample");
        assertEquals(dto.getResultWord().getText(), "зразок");
        assertEquals(dto.getSourceWord().getLangCode(), "en");
        assertEquals(dto.getResultWord().getLangCode(), "uk");
        assertTrue(translationsDto.getFilteredRecords().equals(1L));
        assertTrue(translationsDto.getTotalRecords().equals(1L));
        assertThat(translationsDto.getLangCodesPairs()).hasSize(0);
        assertThat(translationsDto.getPartsOfSpeech()).hasSize(0);
    }

}
