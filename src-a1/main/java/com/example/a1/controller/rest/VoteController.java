package com.example.a1.controller.rest;

import com.example.a1.service.AnswerManagementService;
import com.example.a1.service.QuestionManagementService;
import com.example.a1.service.UserManagementService;
import com.example.a1.service.VoteManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VoteController {
    private final VoteManagementService voteManagementService;
    private final UserManagementService userManagementService;

    @PostMapping("/questions/{questionId}/answers/{answerId}/votes/{voteValue}")
    public void voteAnswer(@PathVariable int answerId, @PathVariable int voteValue) {
        String type = voteValue == 1 ? "up": "down";
        voteManagementService.voteAnswer(userManagementService.loadCurrentUser().getId(), answerId, type);
    }

    @PostMapping("/questions/{questionId}/votes/{voteValue}")
    private void voteQuestion(@PathVariable int questionId, @PathVariable int voteValue) {
        String type = voteValue == 1 ? "up": "down";
        voteManagementService.voteQuestion(userManagementService.loadCurrentUser().getId(), questionId, type);
    }
}