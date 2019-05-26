package com.example.a1.dto;

import com.example.a1.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoggedInDTO {
    private int id;
    private String username;
    private int score;

    @JsonProperty(value = "isModerator")
    private boolean isModerator;

    @JsonProperty(value = "isBlocked")
    private boolean isBlocked;

    public static UserLoggedInDTO ofEntity(User user) {
        UserLoggedInDTO userLoggedInDTO = new UserLoggedInDTO();
        userLoggedInDTO.setId(user.getId());
        userLoggedInDTO.setUsername(user.getUsername());
        userLoggedInDTO.setScore(user.getScore());
        userLoggedInDTO.setModerator(user.isModerator());
        userLoggedInDTO.setBlocked(user.isBanned());

        return userLoggedInDTO;
    }
}