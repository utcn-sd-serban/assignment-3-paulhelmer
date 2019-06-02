package com.example.a1.repository.jpa;

import com.example.a1.entity.Question;
import com.example.a1.repository.QuestionRepository;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HibernateQuestionRepository implements QuestionRepository {
    private final EntityManager entityManager;

    @Override
    public Question save(Question question) {
        if(question.getId() == null) {
            entityManager.persist(question);
            return question;
        }
        else
            return entityManager.merge(question);
    }

    @Override
    public Optional<Question> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Question.class, id));
    }

    @Override
    public void delete(Question question) {
        entityManager.remove(question);

    }

    @Override
    public List<Question> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        query.select(query.from(Question.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public List<Question> findByTitle(String title) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> query = builder.createQuery(Question.class);
        query.select(query.from(Question.class));
        query.where(entityManager.getCriteriaBuilder().like(query.from(Question.class).get("title"),"%"+title+"%"));
        return entityManager.createQuery(query).getResultList();
    }
}