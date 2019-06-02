package com.example.a1.repository.jdbc;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import com.example.a1.repository.QuestionRepository;
import com.example.a1.repository.jdbc.mappers.QuestionMapper;
import com.example.a1.repository.jdbc.mappers.TagMapper;
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
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate template;


    @Override
    public Question save(Question question) {
        if(question.getId() == null)
            question.setId(insert(question));
        else
            update(question);
        return question;
    }

    @Override
    public Optional<Question> findById(Integer id) {
        List<Question> questions = template.query("SELECT * FROM question WHERE id = ?", new QuestionMapper(), id);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void delete(Question question) {
        template.update("DELETE FROM question WEHRE id = ?", question.getId());
    }

    @Override
    public List<Question> findAll() {
        return template.query("SELECT * FROM question", new QuestionMapper());
    }

    @SuppressWarnings("Duplicates")
    private int insert(Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("author_id", question.getAuthorId());
        map.put("title", question.getTitle());
        map.put("text", question.getText());
        map.put("creation_date", question.getCreationDate());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE question SET author_id = ?, title = ?, text = ?, creation_date = ? WHERE id = ?",
                question.getAuthorId(), question.getTitle(), question.getText(), question.getCreationDate(), question.getId());
    }

    public List<Question> findByTitle(String title) {
        return template.query("SELECT * FROM question WHERE title like ?", new QuestionMapper(), "%" + title + "%");
    }


    public void addTagToQuestion(Tag tag, Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_tag");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("question_id", question.getId());
        map.put("tag_id", tag.getId());
        insert.executeAndReturnKey(map).intValue();
    }


}
