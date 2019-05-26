package com.example.a1.events;

import com.example.a1.dto.QuestionDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionCreatedEvent extends BaseEvent {
    private final QuestionDTO questionDTO;

    public QuestionCreatedEvent(QuestionDTO questionDTO) {
        super(EventType.QUESTION_CREATED);
        this.questionDTO = questionDTO;
    }
}
