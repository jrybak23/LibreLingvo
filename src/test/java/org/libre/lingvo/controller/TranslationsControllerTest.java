package org.libre.lingvo.controller;

import org.junit.Test;
import org.libre.lingvo.dto.TranslationsDto;
import org.libre.lingvo.services.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by igorek2312 on 17.12.16.
 */
public class TranslationsControllerTest extends AbstractControllerTest {

    @Autowired
    private TranslationService translationServiceMock;

    @Test
    public void viewUserTranslations() throws Exception {
        String accessToken = getAccessToken(USER_EMAIL, PASSWORD);

        TranslationsDto dto = new TranslationsDto();
        dto.setTranslations(new ArrayList<>());
        dto.setLangCodesPairs(new ArrayList<>());
        dto.setPartsOfSpeech(new ArrayList<>());
        dto.setFilteredRecords(0L);
        dto.setTotalRecords(10L);

        when(translationServiceMock.getUserTranslations(
                USER_ID,
                2,
                15,
                "set",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ))
                .thenReturn(dto);

        mvc.perform(get(API_VERSION + "/users/me/translations")
                .header("Authorization", "Bearer " + accessToken)
                .param("page-index", "2")
                .param("max-records", "15")
                .param("search-substring", "set"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translations", is(empty())))
                .andExpect(jsonPath("$.langCodesPairs", is(empty())))
                .andExpect(jsonPath("$.partsOfSpeech", is(empty())))
                .andExpect(jsonPath("$.filteredRecords", is(0)))
                .andExpect(jsonPath("$.totalRecords", is(10)));
    }

}
