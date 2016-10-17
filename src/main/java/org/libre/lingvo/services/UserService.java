package org.libre.lingvo.services;

import org.libre.lingvo.dto.FullUserDetailsDto;
import org.libre.lingvo.dto.UserDetailsDto;
import org.libre.lingvo.dto.UserRegistrationDto;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */

public interface UserService {
    UserDetailsDto getUserDetails(Long userId);

    List<FullUserDetailsDto> getAllFullUserDetail();

    void registerUser(UserRegistrationDto dto);

    void deleteNotEnabledUsersWithExpiredTokens();
}
