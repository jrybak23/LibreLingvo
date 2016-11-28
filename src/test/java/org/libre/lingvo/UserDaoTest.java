package org.libre.lingvo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by igorek2312 on 27.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserDaoTest {

    public static final String testEmail = "testemail@example.com";

    @Autowired
    private UserDao userDao;


    @Test
    public void testFindByEmailSubstring(){
        String emailSubstring = testEmail.substring(0, 10);
        List<User> list = userDao.findByEmailSubstring(emailSubstring, 1,20);
        assertEquals(list.size(),0);
        persistSample();
        list = userDao.findByEmailSubstring(emailSubstring, 1,20);
        assertEquals(list.size(),1);
    }

    @Test
    public void testExistUserWithEmail(){
        assertFalse(userDao.existWithEmail(testEmail).orElse(false));
        persistSample();
        assertTrue(userDao.existWithEmail(testEmail).orElse(false));
    }

    @Test
    public void testDeleteNotEnabledWithExpiredTokens(){
       userDao.deleteExpiredNotActivatedUsers();
    }

    private void persistSample(){
        User user=new User();
        user.setEmail(testEmail);
        user.setName("test sample");
        userDao.create(user);
    }
}
