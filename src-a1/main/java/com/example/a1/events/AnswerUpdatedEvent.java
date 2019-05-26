package com.example.a1.events;

import com.example.a1.dto.AnswerDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerUpdatedEvent extends BaseEvent {
    private final AnswerDTO answerDTO;

    public AnswerUpdatedEvent(AnswerDTO answerDTO) {
        super(EventType.ANSWER_UPDATED);
        this.answerDTO = answerDTO;
    }
}