package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 21.11.16.
 */
public class ChangePasswordDto {
    private String oldPassword;

    private String password;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
