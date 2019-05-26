package com.example.a1.controller.rest;

import com.example.a1.dto.QuestionDTO;
import com.example.a1.dto.TagDTO;
import com.example.a1.entity.Tag;
import com.example.a1.service.QuestionManagementService;
import com.example.a1.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionManagementService questionManagementService;
    private final UserManagementService userManagementService;

    @GetMapping("/questions")
    public List<QuestionDTO> listQuestions() {
        return questionManagementService.listQuestions();
    }

    @PostMapping("/questions")
    public QuestionDTO addQuestion(@RequestBody QuestionDTO question) {
        Set<Tag> tags = question.getTags().stream().map(Tag::new).collect(Collectors.toSet());
        return questionManagementService.addQuestion(userManagementService.loadCurrentUser().getId(), question.getTitle(), question.getText(), tags);
    }

    @GetMapping("/questions/findByTags/{tagsAsString}")
    public List<QuestionDTO> filterByTag(@PathVariable String tagsAsString) {
        Set<Tag> tags = Arrays.asList(tagsAsString.split(",")).stream().map(Tag::new).collect(Collectors.toSet());
        return questionManagementService.searchQuestionsByTag(tags);
    }

    @GetMapping("/questions/findByTitle/{title}")
    public List<QuestionDTO> handleFilterByTitle(@PathVariable String title) {
        System.out.println("filtering with title: " + title);
        return questionManagementService.searchQuestionsByTitle(title);
    }

    @PutMapping("questions/{questionId}")
    public QuestionDTO handleUpdateQuestion(@PathVariable int questionId, @RequestBody QuestionDTO question) {
        Set<Tag> tags = question.getTags().stream().map(Tag::new).collect(Collectors.toSet());
        return questionManagementService.updateQuestion(userManagementService.loadCurrentUser().getId(), questionId, question.getTitle(), question.getText(), tags);
    }

    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestion(@PathVariable int questionId) {
        questionManagementService.deleteQuestion(userManagementService.loadCurrentUser().getId(), questionId);
    }

    /*@GetMapping("/questions/tags")
    public List<TagDTO> listTags() {
        return questionManagementService.listAllTags();
    }*/
}
