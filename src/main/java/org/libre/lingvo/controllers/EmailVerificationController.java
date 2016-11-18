package org.libre.lingvo.controllers;

import org.libre.lingvo.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by igorek2312 on 06.10.16.
 */
@RestController
@RequestMapping(value = "/api/v1")
public class EmailVerificationController {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @RequestMapping(value = "/verification-tokens/{tokenUuid}/user/enabled", method = RequestMethod.PUT)
    public void enableUser(@PathVariable String tokenUuid){
        verificationTokenService.enableUser(tokenUuid);
    }

    @RequestMapping(value = "/verification-tokens/{tokenUuid}/user", method = RequestMethod.DELETE)
    public void cancelUserEnabling(@PathVariable String tokenUuid){
        verificationTokenService.cancelUserEnabling(tokenUuid);
    }
}
