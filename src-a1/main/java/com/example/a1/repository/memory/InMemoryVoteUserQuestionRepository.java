package com.example.a1.repository.memory;

import com.example.a1.entity.Question;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserQuestion;
import com.example.a1.repository.VoteUserQuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryVoteUserQuestionRepository implements VoteUserQuestionRepository {
    private final Map<Integer, VoteUserQuestion> voteUserQuestionRepository = new ConcurrentHashMap<Integer, VoteUserQuestion>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public VoteUserQuestion save(VoteUserQuestion voteUserQuestion) {
        if (voteUserQuestion.getId() == 0) {
            voteUserQuestion.setId(currentId.getAndIncrement());
        }
        voteUserQuestionRepository.put(voteUserQuestion.getId(), voteUserQuestion);
        return voteUserQuestion;
    }

    @Override
    public Optional<VoteUserQuestion> findById(Integer id) {
        return Optional.ofNullable(voteUserQuestionRepository.get(id));
    }

    @Override
    public void delete(VoteUserQuestion voteUserQuestion) {
        voteUserQuestionRepository.remove(voteUserQuestion.getId());
    }

    @Override
    public List<VoteUserQuestion> findAll() {
        return new ArrayList<VoteUserQuestion>(voteUserQuestionRepository.values());
    }

    public Optional<VoteUserQuestion> findByUserAndQuestion(User user, Question question){
        return Optional.ofNullable(this.findAll().stream().filter(x->x.getUserId() == user.getId() && x.getQuestionId() == question.getId()).collect(Collectors.toList()).get(0));
    }

    public Long getUpVotes(Question question){
        return this.findAll().stream().filter(x->x.getQuestionId() == question.getId() && x.getVoteType().equals("up")).count();
    }
    public Long getDownVotes(Question question){
        return this.findAll().stream().filter(x->x.getQuestionId() == question.getId() && x.getVoteType().equals("down")).count();
    }
}
