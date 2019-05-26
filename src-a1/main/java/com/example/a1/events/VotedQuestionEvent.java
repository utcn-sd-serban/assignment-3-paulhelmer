package com.example.a1.events;

import com.example.a1.dto.QuestionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VotedQuestionEvent extends BaseEvent {
    private final QuestionDTO questionDTO;

    public VotedQuestionEvent(QuestionDTO questionDTO) {
        super(EventType.VOTED_QUESTION);
        this.questionDTO = questionDTO;
    }
}
