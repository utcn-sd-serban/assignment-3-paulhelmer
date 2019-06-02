package com.example.a1.repository.jdbc;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import com.example.a1.repository.TagRepository;
import com.example.a1.repository.jdbc.mappers.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {
    private final JdbcTemplate template;


    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == null) {
            tag.setId(insert(tag));
        } else {
            update(tag);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> tags = template.query("SELECT * FROM tag WHERE id = ?", new TagMapper(), id);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public void delete(Tag tag) {
        template.update("DELETE FROM tag WEHRE id = ?", tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return template.query("SELECT * FROM tag", new TagMapper());
    }

    private int insert(Tag tag) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("tag_name", tag.getTagName());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Tag tag) {
        template.update("UPDATE tag SET tag_name = ? WHERE id = ?", tag.getTagName(), tag.getId());
    }

    public Optional<Tag> findByName(String tagName) {
        List<Tag> tags = template.query("SELECT * FROM tag WHERE tag_name = ?", new TagMapper(), tagName);
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }
    public List<Tag> findTagsByQuestion(Question question) {
        return template.query("select tag.* from tag join question_tag on tag.id = question_tag.tag_id where question_id = ?", new TagMapper(), question.getId());
    }
}
