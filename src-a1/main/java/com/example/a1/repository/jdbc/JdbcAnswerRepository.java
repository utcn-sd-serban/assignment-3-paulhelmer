package com.example.a1.repository.jdbc;

import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import com.example.a1.repository.AnswerRepository;
import com.example.a1.repository.jdbc.mappers.AnswerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {

    private final JdbcTemplate template;


    @Override
    public Answer save(Answer answer) {
        if(answer.getId() == null)
            answer.setId(insert(answer));
        else
            update(answer);
        return answer;
    }

    @Override
    public Optional<Answer> findById(Integer id) {
        List<Answer> answers = template.query("SELECT * FROM answer WHERE id = ?", new AnswerMapper(), id);
        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    @Override
    public void delete(Answer answer) {
        template.update("DELETE FROM answer WHERE id = ?", answer.getId());
    }

    @Override
    public List<Answer> findAll() {
       return template.query("SELECT * FROM answer", new AnswerMapper());
    }

    private Integer insert(Answer answer){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("question_id", answer.getQuestionId());
        map.put("author_id", answer.getAuthorId());
        map.put("text", answer.getText());
        map.put("creation_date", answer.getCreationDate());
        return insert.executeAndReturnKey(map).intValue();

    }

    private void update(Answer answer) {
        template.update("UPDATE answer SET question_id = ?, author_id = ?, text = ?, creation_date = ? WHERE id = ?",
                answer.getQuestionId(), answer.getAuthorId(), answer.getText(), answer.getCreationDate(), answer.getId());
    }

    public List<Answer> findAnswersByQuestion(Question question){
        return template.query("SELECT * FROM answer WHERE question_id = ?",
                new AnswerMapper(), question.getId());
    }
}
