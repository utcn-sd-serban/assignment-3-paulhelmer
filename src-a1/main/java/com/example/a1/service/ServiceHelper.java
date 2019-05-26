package com.example.a1.service;

import com.example.a1.dto.AnswerDTO;
import com.example.a1.dto.QuestionDTO;
import com.example.a1.entity.Answer;
import com.example.a1.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceHelper {
    private final UserManagementService userManagementService;

    public AnswerDTO getAnswerDTO(Answer answer) {
        return AnswerDTO.ofEntity(answer, userManagementService.getUserDTO(answer.getAuthorId()));
    }

    public QuestionDTO getQuestionDTO(Question question) {
        return QuestionDTO.ofEntity(question, userManagementService.getUserDTO(question.getAuthorId()));
    }
}