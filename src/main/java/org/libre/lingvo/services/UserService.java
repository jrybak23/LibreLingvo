package org.libre.lingvo.services;

import org.libre.lingvo.dto.*;
import org.libre.lingvo.entities.User;

import java.util.List;

/**
 * Created by igorek2312 on 08.09.16.
 */

public interface UserService {
    UserDetailsDto getUserDetails(long userId);

    List<UserItemDto> getUserItems(int pageIndex, int maxRecords);

    User registerUser(UserRegistrationDto dto);

    void updateUser(Long userId,UserUpdatingDto dto);

    void updateFullUserInfo(long userId, FullUserDetailsDto dto);

    void deleteUser(long userId);

    void deleteNotEnabledUsersWithExpiredTokens();

    void revokeToken(String accessToken);
}
