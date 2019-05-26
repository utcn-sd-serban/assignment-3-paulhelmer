package com.example.a1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="author_id")
    private Integer authorId;
    @Column
    private String title;
    @Column
    private String text;
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;
    @Transient
    private Long voteCount = Long.valueOf(0);

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "question_tag", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name= "tag_id") )
    private Set<Tag> questionTags = new HashSet<Tag>();

    public Question(int authorId, String title, String text, Set<Tag> tags){
        this.authorId = authorId;
        this.title = title;
        this.text = text;
        this.questionTags = tags;
        this.creationDate = ZonedDateTime.now();
    }
}
