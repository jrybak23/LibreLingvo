package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.libre.lingvo.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by igorek2312 on 24.12.16.
 */
public class TagDaoTest extends AbstractDbTest {
    private final static String SAMPLE_DATA_PATH = "/dao/user/translations-test-case.xml";

    @Autowired
    private TagDao tagDao;

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByUserId() {
        List<Tag> tags = tagDao.findByUserId(1L);
        assertThat(tags)
                .hasSize(2)
                .extracting(Tag::getName)
                .containsExactly("phrasal verbs","empty tag");
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    @ExpectedDatabase(value = "/dao/user/after-removing-translation-tag.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void removeTranslationTag() {
        tagDao.removeTranslationTag(1, 4);
    }
}
