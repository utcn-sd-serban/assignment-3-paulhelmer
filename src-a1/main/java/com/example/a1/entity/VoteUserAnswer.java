package com.example.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "vote_user_answer")
@AllArgsConstructor
@NoArgsConstructor
public class VoteUserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "answer_id")
    private Integer answerId;
    @Column (name = "vote_type")
    private String voteType;

    public VoteUserAnswer(Integer userId, Integer answerId, String voteType) {
        this.userId = userId;
        this.answerId = answerId;
        this.voteType = voteType;
    }
}
