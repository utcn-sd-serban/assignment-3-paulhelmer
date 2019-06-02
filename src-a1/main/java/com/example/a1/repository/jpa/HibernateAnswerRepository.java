package com.example.a1.repository.jpa;

import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import com.example.a1.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateAnswerRepository implements AnswerRepository {
    private final EntityManager entityManager;

    @Override
    public Answer save(Answer answer) {
        if(answer.getId() == null) {
           entityManager.persist(answer);
           return answer;
        }
        else
            return entityManager.merge(answer);
    }

    @Override
    public Optional<Answer> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Answer.class, id));
    }

    @Override
    public void delete(Answer answer) {
        entityManager.remove(answer);

    }

    @Override
    public List<Answer> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Answer> query = builder.createQuery(Answer.class);
        query.select(query.from(Answer.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public List<Answer> findAnswersByQuestion(Question question) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Answer> query = builder.createQuery(Answer.class);
        query.select(query.from(Answer.class));
        query.where(builder.equal(query.from(Answer.class).get("question_id"),question.getId()));
        return entityManager.createQuery(query).getResultList();
    }
}
