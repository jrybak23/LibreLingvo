package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 27.09.16.
 */
public class EmailVerificationDto {
    private String name;

    private String originUrl;

    private String enableUserUrl;

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

    public String getEnableUserUrl() {
        return enableUserUrl;
    }

    public void setEnableUserUrl(String enableUserUrl) {
        this.enableUserUrl = enableUserUrl;
    }

    public String getCancelUserEnablingUrl() {
        return cancelUserEnablingUrl;
    }

    public void setCancelUserEnablingUrl(String cancelUserEnablingUrl) {
        this.cancelUserEnablingUrl = cancelUserEnablingUrl;
    }
}
