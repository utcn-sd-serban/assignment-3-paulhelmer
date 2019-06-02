package com.example.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "vote_user_question")
@AllArgsConstructor
@NoArgsConstructor
public class VoteUserQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "question_id")
    private Integer questionId;
    @Column (name = "vote_type")
    private String voteType;

    public VoteUserQuestion(Integer userId, Integer questionId, String voteType){
        this.questionId = questionId;
        this.userId = userId;
        this.voteType = voteType;
    }
}
