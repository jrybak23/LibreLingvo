package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 27.09.16.
 */
public class EmailVerificationDto {
    private String name;

    private String originUrl;

    private String originEnableUserUrl;

    private String cancelUserEnablingUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getOriginEnableUserUrl() {
        return originEnableUserUrl;
    }

    public void setOriginEnableUserUrl(String originEnableUserUrl) {
        this.originEnableUserUrl = originEnableUserUrl;
    }

    public String getCancelUserEnablingUrl() {
        return cancelUserEnablingUrl;
    }

    public void setCancelUserEnablingUrl(String cancelUserEnablingUrl) {
        this.cancelUserEnablingUrl = cancelUserEnablingUrl;
    }
}
