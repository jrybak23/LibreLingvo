package org.libre.lingvo.services;

import org.libre.lingvo.entities.User;

/**
 * Created by igorek2312 on 27.09.16.
 */
public interface VerificationTokenService {

    void enableUser(String tokenUuid);

    void cancelUserEnabling(String tokenUuid);

    void create(User user, String originUrl);
}
