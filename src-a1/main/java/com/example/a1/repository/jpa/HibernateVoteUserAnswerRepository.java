package com.example.a1.repository.jpa;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserAnswer;
import com.example.a1.repository.VoteUserAnswerRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateVoteUserAnswerRepository implements VoteUserAnswerRepository {
    private final EntityManager entityManager;

    @Override
    public VoteUserAnswer save(VoteUserAnswer voteUserAnswer) {
        if(voteUserAnswer.getId() == null) {
            entityManager.persist(voteUserAnswer);
            return voteUserAnswer;
        }
        else
            return entityManager.merge(voteUserAnswer);
    }

    @Override
    public Optional<VoteUserAnswer> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(VoteUserAnswer.class, id));
    }

    @Override
    public void delete(VoteUserAnswer voteUserAnswer) {
        entityManager.remove(voteUserAnswer);

    }

    @Override
    public List<VoteUserAnswer> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserAnswer> query = builder.createQuery(VoteUserAnswer.class);
        query.select(query.from(VoteUserAnswer.class));
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Optional<VoteUserAnswer> findByUserAndAnswer(User user, Answer answer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserAnswer> query = builder.createQuery(VoteUserAnswer.class);
        query.select(query.from(VoteUserAnswer.class));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("user_id"), user.getId()));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("answer_id"), answer.getId()));
        List<VoteUserAnswer> votes = entityManager.createQuery(query).getResultList();
        return Optional.ofNullable(votes.get(0));
    }

    @Override
    public Long getUpVotes(Answer answer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserAnswer> query = builder.createQuery(VoteUserAnswer.class);
        query.select(query.from(VoteUserAnswer.class));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("answer_id"), answer.getId()));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("vote_type"), "up"));
        return (long) entityManager.createQuery(query).getResultList().size();
    }

    @Override
    public Long getDownVotes(Answer answer) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VoteUserAnswer> query = builder.createQuery(VoteUserAnswer.class);
        query.select(query.from(VoteUserAnswer.class));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("answer_id"), answer.getId()));
        query.where(builder.equal(query.from(VoteUserAnswer.class).get("vote_type"), "down"));
        return (long) entityManager.createQuery(query).getResultList().size();
    }
}
