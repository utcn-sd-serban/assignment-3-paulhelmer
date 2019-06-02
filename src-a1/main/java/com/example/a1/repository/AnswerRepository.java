package com.example.a1.repository;

import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;

import java.util.List;

public interface AnswerRepository extends RepositoryInterface<Answer>{

    public List<Answer> findAnswersByQuestion(Question question);
}
