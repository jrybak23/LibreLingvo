package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.libre.lingvo.entities.Word;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 24.12.16.
 */
public class WordDaoTest extends AbstractDbTest {
    private final static String SAMPLE_DATA_PATH = "/dao/user/translations-test-case.xml";

    @Autowired
    private WordDao wordDao;

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByTextAndLangCode() {
        Word word = wordDao.findByTextAndLangCode("sample", "en").get();
        assertEquals(word.getText(), "sample");
        assertEquals(word.getLangCode(), "en");
    }
}
