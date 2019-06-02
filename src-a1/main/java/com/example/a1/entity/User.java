package com.example.a1.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(of = {"score", "id", "username"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    private boolean isBanned = false;
    private boolean isLogged = false;
    private boolean isModerator = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getScore(){
        return 0;
    }

}
