package com.example.a1.repository.jdbc;

import com.example.a1.entity.Question;
import com.example.a1.entity.User;
import com.example.a1.entity.VoteUserQuestion;
import com.example.a1.repository.VoteUserQuestionRepository;
import com.example.a1.repository.jdbc.mappers.VoteUserQuestionMapper;
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
public class JdbcVoteUserQuestionRepository implements VoteUserQuestionRepository {
    private final JdbcTemplate template;

    @Override
    public VoteUserQuestion save(VoteUserQuestion voteUserQuestion) {
        if(voteUserQuestion.getId() == null)
            voteUserQuestion.setId(insert(voteUserQuestion));
        else
            update(voteUserQuestion);
        return voteUserQuestion;
    }

    @Override
    public Optional<VoteUserQuestion> findById(Integer id) {
        List<VoteUserQuestion> voteUserQuestions = template.query("SELECT * FROM vote_user_question WHERE id = ?", new VoteUserQuestionMapper(), id);
        return voteUserQuestions.isEmpty() ? Optional.empty() : Optional.of(voteUserQuestions.get(0));
    }

    @Override
    public void delete(VoteUserQuestion voteUserQuestion) {
        template.update("SELECT * FROM vote_user_question WHERE id = ?", voteUserQuestion.getId());
    }

    @Override
    public List<VoteUserQuestion> findAll() {
        return template.query("SELECT * FROM vote_user_question", new VoteUserQuestionMapper());
    }

    private Integer insert (VoteUserQuestion voteUserQuestion){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_user_question");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("question_id", voteUserQuestion.getQuestionId());
        map.put("user_id", voteUserQuestion.getUserId());
        map.put("vote_type", voteUserQuestion.getVoteType());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update (VoteUserQuestion voteUserQuestion){
        template.update("UPDATE vote_user_question SET question_id = ?, user_id = ?, vote_type = ?", voteUserQuestion.getQuestionId(), voteUserQuestion.getUserId(), voteUserQuestion.getVoteType());
    }

    public Optional <VoteUserQuestion> findByUserAndQuestion(User user, Question question){
        List<VoteUserQuestion> voteUserQuestions = template.query("SELECT * FROM vote_user_question WHERE user_id = ? and question_id = ?", new VoteUserQuestionMapper(), user.getId(),  question.getId());
        return voteUserQuestions.isEmpty() ? Optional.empty() : Optional.of(voteUserQuestions.get(0));
    }

    public Long getUpVotes(Question question){
        return template.queryForObject("SELECT COUNT(*) FROM vote_user_question WHERE question_id = ? and vote_type = 'up' ", Integer.class, question.getId()).longValue();
    }

    public Long getDownVotes(Question question) {
        return template.queryForObject("SELECT COUNT(*) FROM vote_user_question WHERE question_id = ? and vote_type = 'down' ", Integer.class, question.getId()).longValue();
    }



}
