package org.libre.lingvo.config.startup.events;

import org.libre.lingvo.dao.RoleDao;
import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.libre.lingvo.utils.ReadOnlyAccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by igorek2312 on 23.09.16.
 */

@Profile({"init", "dev", "cloud"})
@Component
@Transactional
public class DatabasePopulator implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    private Role findOrCreateRole(String roleName) {
        return roleDao.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            roleDao.create(role);
            return role;
        });
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role roleUser = findOrCreateRole("ROLE_USER");
        Role roleAdmin = findOrCreateRole("ROLE_ADMIN");

       /*User admin=new User();
        admin.setName("sample");
        admin.setEmail("sample@example.com");
        admin.setPassword("1234");
        admin.setEnabled(true);
        admin.setNonLocked(true);
        UserRole userRole = new UserRole();
        userRole.setRole(roleAdmin);
        userRole.setUser(admin);
        admin.getUserRoles().add(userRole);
        userDao.create(admin);*/

        String email = ReadOnlyAccountUtil.getReadOnlyUserEmail();
        if (!userDao.existsWithEmail(email)) {
            User readOnly = new User();
            readOnly.setName("readonly");
            readOnly.setEmail(email);
            readOnly.setPassword("1234");
            readOnly.setEnabled(true);
            readOnly.setRegistrationDate(new Date());

            UserRole userRole = new UserRole();
            userRole.setRole(roleUser);
            userRole.setUser(readOnly);
            readOnly.getUserRoles().add(userRole);

            userDao.create(readOnly);
        }
    }
}
