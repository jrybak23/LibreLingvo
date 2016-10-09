package org.libre.lingvo.config.events;

import org.libre.lingvo.dao.RoleDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by igorek2312 on 23.09.16.
 */

@Profile("init")
@Component
@Transactional
public class DatabasePopulator  implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role roleUser=new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin=new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleDao.create(roleUser);
        roleDao.create(roleAdmin);

        User admin=new User();
        admin.setName("sample");
        admin.setEmail("sample@exaple.com");
        admin.setPassword("1234");
        admin.setEnabled(true);

        UserRole userRole = new UserRole();
        userRole.setRole(roleAdmin);
        userRole.setUser(admin);
        admin.getUserRoles().add(userRole);

        userDao.create(admin);
    }
}
