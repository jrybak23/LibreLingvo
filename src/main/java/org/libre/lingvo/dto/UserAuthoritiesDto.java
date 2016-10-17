package org.libre.lingvo.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by igorek2312 on 15.10.16.
 */
public class UserAuthoritiesDto {
    private Set<String> authorities=new HashSet<>();

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
