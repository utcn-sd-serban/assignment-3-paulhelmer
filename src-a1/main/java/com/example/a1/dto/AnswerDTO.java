package com.example.a1.dto;

import com.example.a1.entity.Answer;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class AnswerDTO {
    private int id;
    private UserDTO author;
    private String text;
    private String creationDateTime;
    private int questionId;
    private Long voteCount;

    public static AnswerDTO ofEntity(Answer answer, UserDTO author) {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setAuthor(author);
        answerDTO.setText(answer.getText());
        answerDTO.setCreationDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm").format(answer.getCreationDate()));
        answerDTO.setQuestionId(answer.getQuestionId());
        answerDTO.setVoteCount(answer.getVoteCount());

        return answerDTO;
    }
}
