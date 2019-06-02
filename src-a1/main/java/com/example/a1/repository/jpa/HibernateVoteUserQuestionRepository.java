package com.example.a1.repository.jpa;

import com.example.a1.entity.*;
import com.example.a1.repository.VoteUserQuestionRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateVoteUserQuestionRepository implements VoteUserQuestionRepository {
    private final EntityManager entityManager;

    @Override
    public VoteUserQuestion save(VoteUserQuestion voteUserQuestion) {
        if(voteUserQuestion.getId() == null) {
            entityManager.persist(voteUserQuestion);
            return voteUserQuestion;
        }
        else
            return entityManager.merge(voteUserQuestion);
    }

    @Override
    public Optional<VoteUserQuestion> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(VoteUserQuestion.class, id));
    }

    @Override
    public void delete(VoteUserQuestion voteUserQuestion) {
        entityManager.remove(voteUserQuestion);

    }

    @Override
    public List<VoteUserQuestion> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserQuestion> query = builder.createQuery(VoteUserQuestion.class);
        query.select(query.from(VoteUserQuestion.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Optional<VoteUserQuestion> findByUserAndQuestion(User user, Question question) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserQuestion> query = builder.createQuery(VoteUserQuestion.class);
        query.select(query.from(VoteUserQuestion.class));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("user_id"), user.getId()));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("question_id"), question.getId()));
        List<VoteUserQuestion> votes = entityManager.createQuery(query).getResultList();
        return Optional.ofNullable(votes.get(0));
    }

    @Override
    public Long getUpVotes(Question question) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserQuestion> query = builder.createQuery(VoteUserQuestion.class);
        query.select(query.from(VoteUserQuestion.class));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("question_id"), question.getId()));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("vote_type"), "up"));
        return (long) entityManager.createQuery(query).getResultList().size();
    }

    @Override
    public Long getDownVotes(Question question) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserQuestion> query = builder.createQuery(VoteUserQuestion.class);
        query.select(query.from(VoteUserQuestion.class));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("question_id"), question.getId()));
        query.where(builder.equal(query.from(VoteUserQuestion.class).get("vote_type"), "down"));
        return (long) entityManager.createQuery(query).getResultList().size();
    }
}
