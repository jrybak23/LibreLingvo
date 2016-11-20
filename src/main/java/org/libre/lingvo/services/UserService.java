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

    User createUser(UserRegistrationDto dto);

    void activateUser(String activationKey);

    void cancelActivation(String activationKey);

    void updateUser(long userId, UserUpdatingDto dto);

    void updateFullUserInfo(long userId, FullUserDetailsDto dto);

    void changePassword(long userId, ChangePasswordDto dto);

    void deleteUser(long userId);

    void deleteExpiredNotActivatedUsers();

    void revokeToken(String accessToken);

    String generateResetKey(String email);

    void resetPassword(String resetKey, String newPassword);
}
