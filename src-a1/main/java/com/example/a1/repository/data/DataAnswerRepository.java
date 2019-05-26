package com.example.a1.repository.data;

import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import com.example.a1.repository.AnswerRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DataAnswerRepository extends Repository<Answer,Integer>, AnswerRepository {

    List<Answer> findAnswersByQuestionId(Integer questionId);

    @Override
    default List<Answer> findAnswersByQuestion(Question question){
        return findAnswersByQuestionId(question.getId());
    }
}
