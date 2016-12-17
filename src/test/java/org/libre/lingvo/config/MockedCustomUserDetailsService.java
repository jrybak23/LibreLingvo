package org.libre.lingvo.config;

import org.libre.lingvo.entities.Role;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by igorek2312 on 16.12.16.
 */
@Service
public class MockedCustomUserDetailsService implements UserDetailsService {
    private Set<User> users =new HashSet<>();

    @PostConstruct
    private void initMockedUsers() {
        Role roleUser=new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin=new Role();
        roleAdmin.setName("ROLE_ADMIN");

        User user = new User();
        user.setId(1L);
        user.setEmail("testemail@example.com");
        user.setPassword("password");
        user.setName("Test");
        user.setEnabled(true);
        user.getUserRoles().add(new UserRole(user,roleUser));

        users.add(user);

        user=new User();
        user.setId(2L);
        user.setEmail("admin@example.com");
        user.setPassword("password");
        user.setName("Admin");
        user.setEnabled(true);
        user.getUserRoles().add(new UserRole(user,roleAdmin));

        users.add(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= users.stream()
                .filter(u -> u.getEmail().equals(username))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        return new UserDaoUserDetails(user);
    }

    private static final class UserDaoUserDetails extends User implements UserDetails, Serializable {

        private UserDaoUserDetails(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getUserRoles().stream().map(UserRole::getRole).collect(Collectors.toSet());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return isNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return super.isEnabled();
        }
    }
}
