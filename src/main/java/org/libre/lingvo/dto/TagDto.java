package org.libre.lingvo.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by igorek2312 on 03.12.16.
 */
public class TagDto {

    private Long id;

    @NotBlank(message = "{NotBlank.tagDto.name}")
    @Size(max = 50, message = "{Size.tagDto.name}")
    private String name;

    private int position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
