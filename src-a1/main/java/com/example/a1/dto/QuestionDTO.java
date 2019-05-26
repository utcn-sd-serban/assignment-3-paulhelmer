package com.example.a1.dto;

import com.example.a1.entity.Question;
import com.example.a1.entity.Tag;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {
    private int id;
    private UserDTO author;
    private String title;
    private String text;
    private String creationDateTime;
    private List<String> tags;
    private Long voteCount;

    public static QuestionDTO ofEntity(Question question, UserDTO author) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setAuthor(author);
        questionDTO.setTitle(question.getTitle());
        questionDTO.setText(question.getText());
        questionDTO.setCreationDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(question.getCreationDate()));
        questionDTO.setVoteCount(question.getVoteCount());

        if (!CollectionUtils.isEmpty(question.getQuestionTags())) {
            questionDTO.setTags(question.getQuestionTags().stream()
                    .map(Tag::toString)
                    .collect(Collectors.toList()));
        }

        return questionDTO;
    }
}
