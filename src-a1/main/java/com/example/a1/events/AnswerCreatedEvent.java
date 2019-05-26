package com.example.a1.events;

import com.example.a1.dto.AnswerDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerCreatedEvent extends BaseEvent {
    private final AnswerDTO answerDTO;

    public AnswerCreatedEvent(AnswerDTO answerDTO) {
        super(EventType.ANSWER_CREATED);
        this.answerDTO = answerDTO;
    }
}