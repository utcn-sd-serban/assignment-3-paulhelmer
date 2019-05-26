package com.example.a1.controller.rest;

import com.example.a1.dto.AnswerDTO;
import com.example.a1.service.AnswerManagementService;
import com.example.a1.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerManagementService answerManagementService;
    private final UserManagementService userManagementService;

    @PostMapping("/questions/{questionId}/answers")
    public AnswerDTO addAnswer(@PathVariable int questionId, @RequestBody AnswerDTO answer) {
        return answerManagementService.addAnswer(userManagementService.loadCurrentUser().getId(), questionId, answer.getText());
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public void deleteAnswer(@PathVariable int answerId) {
        answerManagementService.deleteAnswer(userManagementService.loadCurrentUser().getId(), answerId);
    }

    @PutMapping("questions/{questionId}/answers/{answerId}")
    public AnswerDTO updateAnswer(@PathVariable int answerId, @RequestBody AnswerDTO answer) {
        return answerManagementService.editAnswer(userManagementService.loadCurrentUser().getId(), answerId, answer.getText());
    }

    @GetMapping("/questions/{questionId}/answers")
    public List<AnswerDTO> seeQuestionAnswers(@PathVariable int questionId) {
        return answerManagementService.listAnswersForQuestion(questionId);
    }
}
