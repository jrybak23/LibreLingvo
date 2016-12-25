package org.libre.lingvo.services;

import org.libre.lingvo.dao.UserDao;
import org.libre.lingvo.entities.User;
import org.libre.lingvo.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by igorek2312 on 08.09.16.
 */
@Service
@Profile({"init", "dev", "cloud"})
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
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
