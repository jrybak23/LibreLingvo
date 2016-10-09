package org.libre.lingvo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.dao.VerificationTokenDao;
import org.libre.lingvo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by igorek2312 on 27.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    @Test
    public void testFindByEmailSubstring(){
        List<User> l = dao.findByEmailSubstring("sample", 1,20);
        assertTrue(l.size()>0);
    }
}
