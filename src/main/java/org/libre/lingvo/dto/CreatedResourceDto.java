package org.libre.lingvo.dto;

/**
 * Created by igorek2312 on 01.11.16.
 */
public class CreatedResourceDto {
    private Object id;

    public CreatedResourceDto(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}
