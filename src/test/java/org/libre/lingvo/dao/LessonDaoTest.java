package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.libre.lingvo.entities.Lesson;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 24.12.16.
 */
public class LessonDaoTest extends AbstractDbTest {
    private final static String SAMPLE_DATA_PATH = "/dao/user/translations-test-case.xml";

    @Autowired
    private LessonDao lessonDao;

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByUserId() {
        List<Lesson> lessons = lessonDao.findByUserId(1L);
        assertThat(lessons)
                .hasSize(1);

        assertEquals(lessons.get(0).getCompletedPartsOfLesson(), 1);
        assertEquals(lessons.get(0).getMaxPartsOfLesson(), 3);
    }


}
