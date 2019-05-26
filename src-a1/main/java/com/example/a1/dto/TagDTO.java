package com.example.a1.dto;

import com.example.a1.entity.Tag;
import lombok.Data;

@Data
public class TagDTO {
    private final String value;

    public static TagDTO ofEntity(Tag tag) {
        return new TagDTO(tag.getTagName());
    }
}