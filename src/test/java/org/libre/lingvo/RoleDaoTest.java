package org.libre.lingvo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.libre.lingvo.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by igorek2312 on 27.09.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RoleDaoTest {

    @Autowired
    private RoleDao dao;

    @Test
    public void testFindRoleByName(){
        assertNotNull(dao.findByName("ROLE_USER"));
    }

}
