package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 10.09.16.
 */
public class UserRegistrationDto {
    @Email(message = "{Email.userRegistrationDto.email}")
    private String email;

    @Pattern(regexp = "[A-Za-z0-9-_]+", message = "{Pattern.userRegistrationDto.password}")
    @Size(min = 4, max = 15, message = "{Size.userRegistrationDto.password}")
    private String password;

    @Size(max = 15, message = "{Size.userRegistrationDto.name}")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

