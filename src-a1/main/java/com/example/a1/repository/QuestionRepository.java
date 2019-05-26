package com.example.a1.repository;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;

import java.util.List;

public interface QuestionRepository extends RepositoryInterface<Question> {

    List<Question> findByTitle(String title);

}
