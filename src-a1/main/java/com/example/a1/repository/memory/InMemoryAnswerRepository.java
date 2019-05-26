package com.example.a1.repository.memory;

import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import com.example.a1.repository.AnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryAnswerRepository implements AnswerRepository {
    private final Map<Integer,Answer> answerRepository = new ConcurrentHashMap<Integer, Answer>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == 0) {
            answer.setId(currentId.getAndIncrement());
        }
        answerRepository.put(answer.getId(), answer);
        return answer;
    }

    @Override
    public Optional<Answer> findById(Integer id) {
        return Optional.ofNullable(answerRepository.get(id));
    }

    @Override
    public void delete(Answer answer) {
        answerRepository.remove(answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        return new ArrayList<Answer>(answerRepository.values());
    }

    public List <Answer> findAnswersByQuestion(Question question) {
        return this.findAll().stream().filter(x->x.getQuestionId() == question.getId()).collect(Collectors.toList());
    }
}
