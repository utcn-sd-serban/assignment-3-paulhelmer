package com.example.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "author_id")
    private Integer authorId;
    @Column(name = "question_id")
    private Integer questionId;
    @Column
    private String text;
    @Column (name = "creation_date")
    private ZonedDateTime creationDate;
    @Transient
    private Long voteCount = Long.valueOf(0);

    public Answer(int authorId, int questionId, String text){
        this.authorId = authorId;
        this.questionId = questionId;
        this.text = text;
        this.creationDate = ZonedDateTime.now();
    }
}
