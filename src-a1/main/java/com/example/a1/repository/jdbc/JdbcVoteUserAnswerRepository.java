package com.example.a1.repository.jdbc;

import com.example.a1.entity.Answer;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserAnswer;
import com.example.a1.repository.VoteUserAnswerRepository;
import com.example.a1.repository.jdbc.mappers.VoteUserAnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcVoteUserAnswerRepository implements VoteUserAnswerRepository {
    private final JdbcTemplate template;

    @Override
    public VoteUserAnswer save(VoteUserAnswer voteUserAnswer) {
        if(voteUserAnswer.getId() == null)
            voteUserAnswer.setId(insert(voteUserAnswer));
        else
            update(voteUserAnswer);
        return voteUserAnswer;
    }

    @Override
    public Optional<VoteUserAnswer> findById(Integer id) {
        List<VoteUserAnswer> voteUserAnswers = template.query("SELECT * FROM vote_user_answer WHERE id = ?", new VoteUserAnswerMapper(), id);
        return voteUserAnswers.isEmpty() ? Optional.empty() : Optional.of(voteUserAnswers.get(0));
    }

    @Override
    public void delete(VoteUserAnswer voteUserAnswer) {
        template.update("SELECT * FROM vote_user_answer WHERE id = ?", voteUserAnswer.getId());
    }

    @Override
    public List<VoteUserAnswer> findAll() {
        return template.query("SELECT * FROM vote_user_answer", new VoteUserAnswerMapper());
    }

    private Integer insert (VoteUserAnswer voteUserAnswer){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_user_answer");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("answer_id", voteUserAnswer.getAnswerId());
        map.put("user_id", voteUserAnswer.getUserId());
        map.put("vote_type", voteUserAnswer.getVoteType());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update (VoteUserAnswer voteUserAnswer){
        template.update("UPDATE vote_user_answer SET answer_id = ?, user_id = ?, vote_type = ?", voteUserAnswer.getAnswerId(), voteUserAnswer.getUserId(), voteUserAnswer.getVoteType());
    }

    public Optional <VoteUserAnswer> findByUserAndAnswer(User user, Answer answer){
        List<VoteUserAnswer> voteUserAnswers = template.query("SELECT * FROM vote_user_answer WHERE user_id = ? and answer_id = ?", new VoteUserAnswerMapper(), user.getId(),  answer.getId());
        return voteUserAnswers.isEmpty() ? Optional.empty() : Optional.of(voteUserAnswers.get(0));
    }

    public Long getUpVotes(Answer answer){
        return template.queryForObject("SELECT COUNT(*) FROM vote_user_answer WHERE answer_id = ? and vote_type = 'up' ", Integer.class, answer.getId()).longValue();
    }

    public Long getDownVotes(Answer answer) {
        return template.queryForObject("SELECT COUNT(*) FROM vote_user_answer WHERE answer_id = ? and vote_type = 'down' ", Integer.class, answer.getId()).longValue();
    }



}
