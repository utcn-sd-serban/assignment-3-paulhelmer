package com.example.a1.repository.data;

import com.example.a1.entity.Question;
import com.example.a1.repository.QuestionRepository;
import org.springframework.data.repository.Repository;

public interface DataQuestionRepository  extends Repository<Question,Integer>, QuestionRepository {
}
