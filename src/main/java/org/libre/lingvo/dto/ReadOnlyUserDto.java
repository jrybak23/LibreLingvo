package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 29.11.16.
 */
public class ReadOnlyUserDto {
    private boolean enabled;

    public ReadOnlyUserDto() {
    }

    public ReadOnlyUserDto(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
