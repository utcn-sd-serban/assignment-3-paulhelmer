package com.example.a1.events;

import com.example.a1.dto.AnswerDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VotedAnswerEvent extends BaseEvent {
    private final AnswerDTO answerDTO;

    public VotedAnswerEvent(AnswerDTO answerDTO) {
        super(EventType.VOTED_ANSWER);
        this.answerDTO = answerDTO;
    }
}
