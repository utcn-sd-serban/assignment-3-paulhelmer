package com.example.a1.repository.memory;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserAnswer;
import com.example.a1.repository.VoteUserAnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryVoteUserAnswerRepository implements VoteUserAnswerRepository {
    private final Map<Integer, VoteUserAnswer> voteUserAnswerRepository = new ConcurrentHashMap<Integer, VoteUserAnswer>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public VoteUserAnswer save(VoteUserAnswer voteUserAnswer) {
        if (voteUserAnswer.getId() == 0) {
            voteUserAnswer.setId(currentId.getAndIncrement());
        }
        voteUserAnswerRepository.put(voteUserAnswer.getId(), voteUserAnswer);
        return voteUserAnswer;
    }

    @Override
    public Optional<VoteUserAnswer> findById(Integer id) {
        return Optional.ofNullable(voteUserAnswerRepository.get(id));
    }

    @Override
    public void delete(VoteUserAnswer voteUserAnswer) {
        voteUserAnswerRepository.remove(voteUserAnswer.getId());
    }

    @Override
    public List<VoteUserAnswer> findAll() {
        return new ArrayList<VoteUserAnswer>(voteUserAnswerRepository.values());
    }

    public Optional<VoteUserAnswer> findByUserAndAnswer(User user, Answer answer){
        return Optional.ofNullable(this.findAll().stream().filter(x->x.getUserId() == user.getId() && x.getAnswerId() == answer.getId()).collect(Collectors.toList()).get(0));
    }

    public Long getUpVotes(Answer answer){
        return this.findAll().stream().filter(x->x.getAnswerId() == answer.getId() && x.getVoteType().equals("up")).count();
    }
    public Long getDownVotes(Answer answer){
        return this.findAll().stream().filter(x->x.getAnswerId() == answer.getId() && x.getVoteType().equals("down")).count();
    }
}
