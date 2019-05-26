package com.example.a1.repository.memory;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import com.example.a1.repository.QuestionRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryQuestionRepository implements QuestionRepository {
    private final Map<Integer, Question> questionRepository = new ConcurrentHashMap<Integer, Question>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public Question save(Question question) {
        if (question.getId() == 0) {
            question.setId(currentId.getAndIncrement());
        }
        questionRepository.put(question.getId(), question);
        return question;
    }

    @Override
    public Optional<Question> findById(Integer id) {
        return Optional.ofNullable(questionRepository.get(id));
    }

    @Override
    public void delete(Question question) {
        questionRepository.remove(question.getId());
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<Question>(questionRepository.values());
    }

    public List<Question> findByTitle(String title) {
        return this.findAll().stream().filter(x->x.getTitle().contains(title)).collect(Collectors.toList());
    }


}
