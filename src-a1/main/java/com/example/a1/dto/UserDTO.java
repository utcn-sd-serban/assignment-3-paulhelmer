package com.example.a1.dto;


import com.example.a1.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private int score;

    public static UserDTO ofEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setScore(user.getScore());

        return userDTO;
    }
}
