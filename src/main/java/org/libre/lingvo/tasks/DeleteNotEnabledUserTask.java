package org.libre.lingvo.tasks;

import org.libre.lingvo.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

/**
 * Created by igorek2312 on 07.10.16.
 */
@Component
@Scope("prototype")
public class DeleteNotEnabledUserTask extends TimerTask {

    @Autowired
    private VerificationTokenService verificationTokenService;

    private String tokenUuid;

    @Override
    public void run() {
        verificationTokenService.cancelUserEnabling(tokenUuid);
    }

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }
}