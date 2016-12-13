package org.libre.lingvo.dao;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.libre.lingvo.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 27.09.16.
 */


public class RoleDaoTest extends AbstractDbTest {

    @Autowired
    private RoleDao dao;

    @Test
    @DatabaseSetup("/dao/user/users.xml")
    public void testFindRoleByName(){
        String roleName = "ROLE_USER";
        Role role = dao.findByName(roleName).get();
        assertEquals(role.getName(), roleName);
    }

}
