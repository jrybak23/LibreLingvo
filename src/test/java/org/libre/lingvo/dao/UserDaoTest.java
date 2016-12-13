package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


/**
 * Created by igorek2312 on 27.09.16.
 */

public class UserDaoTest extends AbstractDbTest {
    private final static String SAMPLE_DATA_PATH ="/dao/user/users.xml";
    private static final String testEmail = "testemail@example.com";
    private static final String expiredEmail = "expired@example.com";
    private static final String notExpiredEmail = "notexpired@example.com";

    @Autowired
    private UserDao userDao;

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByEmail() {
        User user = userDao.findByEmail(testEmail).get();
        assertEquals(user.getEmail(), testEmail);
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    @ExpectedDatabase(value = "/dao/user/after-delete-expired-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteExpiredNotActivatedUsers() {
        userDao.deleteExpiredNotActivatedUsers();
        userDao.flash();
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void existsWithEmail() {
        assertTrue(userDao.existsWithEmail(testEmail));
        assertFalse(userDao.existsWithEmail("fake@example.com"));
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findUsersPagination() {
        assertThat(userDao.findUsers(1, 2))
                .hasSize(2);

        assertThat(userDao.findUsers(2, 2))
                .hasSize(1);
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findUsers() {
        assertThat(userDao.findUsers(1, 20))
                .extracting(User::getEmail)
                .containsExactlyInAnyOrder(testEmail, expiredEmail, notExpiredEmail);
    }


    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByActivationKey() {
        String activationKey = "foo-activation-key";
        User user = userDao.findByActivationKey(activationKey).get();
        assertEquals(user.getActivationKey(), activationKey);

        activationKey = "not-activation-key";
        assertFalse(userDao.findByActivationKey(activationKey).isPresent());
    }

    @Test
    @DatabaseSetup(SAMPLE_DATA_PATH)
    public void findByResetKey() {
        String resetKey = "foo-reset-key";
        User user = userDao.findByResetKey(resetKey).get();
        assertEquals(user.getResetKey(), resetKey);

        resetKey = "not-reset-key";
        assertFalse(userDao.findByResetKey(resetKey).isPresent());
    }
}
