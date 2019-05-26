package com.example.a1.controller;

import com.example.a1.entity.Tag;
import com.example.a1.entity.User;
import com.example.a1.exception.NotAllowedException;
import com.example.a1.service.AnswerManagementService;
import com.example.a1.service.QuestionManagementService;
import com.example.a1.service.UserManagementService;
import com.example.a1.service.VoteManagementService;
import jdk.nashorn.internal.objects.Global;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Data
public class ConsoleController implements CommandLineRunner {

    private final Scanner scanner = new Scanner(System.in);
    private final AnswerManagementService answerManagementService;
    private final QuestionManagementService questionManagementService;
    private final UserManagementService userManagementService;
    private final VoteManagementService voteManagementService;
    private final PasswordEncoder passwordEncoder;
    private User user;

    @Override
    public void run(String... args) throws Exception {

        loginUser();
        processCommands();
    }

    private void loginUser() {
        System.out.print("Welcome! Please login\n");
        boolean done = false;
        while(!done) {
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            user =  userManagementService.login(username, password, passwordEncoder);
            if(user.isBanned()){
                System.out.print("\nYou are banned!\n");
                return;
            }
            else
                System.out.print(user.toString() + " is now logged in!");
            done = true;

        }
    }

    private void processCommands(){

        System.out.print("\n\nSelect a command: \n");
        System.out.print("0) Log out\n");
        System.out.print("1) Ask question\n");
        System.out.print("2) Display questions\n");
        System.out.print("3) Search question by title\n");
        System.out.print("4) Search question by tag\n");
        System.out.print("5) Delete question\n");
        System.out.print("6) Edit question\n");
        System.out.print("7) Answer a question\n");
        System.out.print("8) Edit answer\n");
        System.out.print("9) Delete answer\n");
        System.out.print("10)View answers\n");
        System.out.print("11)Vote question\n");
        System.out.print("12)Vote answer\n");

        while(true){

            Integer command = Integer.valueOf(scanner.nextLine().trim());
            handleCommand(command);
        }
    }

    private void handleCommand(Integer command){
        switch(command){
            case 0: logoutUser(); break;
            case 1: askQuestionCommand(); break;
            case 2: displayAllQuestionsCommand(); break;
            case 3: searchQuestionByTitleCommand(); break;
            case 4: searchQuestionByTagCommand(); break;
            case 5: deleteQuestionCommand();break;
            case 6: editQuestionCommand();break;
            case 7: answerQuestionCommand();break;
            case 8: editAnswerCommand();break;
            case 9: deleteAnswerCommand();break;
            case 10:viewAllAnswersForAQuestionCommand();break;
            case 11:voteQuestionCommand();break;
            case 12:voteAnswerCommand();break;
            default: throw new NotAllowedException();
        }
    }

    private void logoutUser(){
        user.setLogged(false);
        loginUser();
        System.out.print("\nSelect a command: ");
    }
    private void askQuestionCommand(){

        System.out.print("Title: ");
        String title  = scanner.nextLine().trim();

        System.out.print("Text: ");
        String text = scanner.nextLine().trim();

        System.out.print("Tags(separated with a space): ");
        Set<Tag> tags = new HashSet<Tag>();
        Arrays.asList(scanner.nextLine().trim().split(" ")).forEach(t->tags.add(new Tag(t)));

        questionManagementService.addQuestion(user.getId(), title, text, tags);
        System.out.print("\nQuestion added! Select another option: " );
    }

    private void displayAllQuestionsCommand(){
        System.out.print(questionManagementService.listQuestions().toString());
        System.out.print("\nChoose another option: ");
    }

    private void searchQuestionByTitleCommand(){
        System.out.print("\nTitle: ");
        String title = scanner.nextLine().trim();
        System.out.print(questionManagementService.searchQuestionsByTitle(title).toString());
        System.out.print("\nChoose another option: ");
    }

    private void searchQuestionByTagCommand(){
        System.out.print("\nTags(separated with space): ");
        Set<Tag> tags = new HashSet<Tag>();
        Arrays.asList(scanner.nextLine().trim().split(" ")).forEach(t->tags.add(new Tag(t)));
        System.out.print(questionManagementService.searchQuestionsByTag(tags).toString());
        System.out.print("\nChoose another option: ");

    }

    private void deleteQuestionCommand(){
        System.out.print("\nQuestion id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());
        questionManagementService.deleteQuestion(user.getId(), id);
        System.out.print("\nChoose another option: ");

    }

    private void editQuestionCommand() {
        System.out.print("\nQuestion id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print("New title: ");
        String title  = scanner.nextLine().trim();

        System.out.print("New text: ");
        String text = scanner.nextLine().trim();

        System.out.print("New tags(separated with a space): ");
        Set<Tag> tags = new HashSet<Tag>();
        Arrays.asList(scanner.nextLine().trim().split(" ")).forEach(t->tags.add(new Tag(t)));

        questionManagementService.updateQuestion(user.getId(), id, title, text, tags);
        System.out.print("\nChoose another option: ");

    }

    private void answerQuestionCommand(){
        System.out.print("\nQuestion id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print("New text: ");
        String text = scanner.nextLine().trim();

        answerManagementService.addAnswer(user.getId(), id, text);
        System.out.print("\nChoose another option: ");

    }

    private void editAnswerCommand(){
        System.out.print("\nAnswer id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print("New text: ");
        String text = scanner.nextLine().trim();

        answerManagementService.editAnswer(id, user.getId(), text);
        System.out.print("\nChoose another option: ");

    }

    private void deleteAnswerCommand() {
        System.out.print("\nAnswer id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        answerManagementService.deleteAnswer(id, user.getId());
        System.out.print("\nChoose another option: ");

    }

    private void viewAllAnswersForAQuestionCommand(){
        System.out.print("\nQuestion id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print(answerManagementService.listAnswersForQuestion(id).toString());
        System.out.print("\nChoose another option: ");

    }

    private void voteQuestionCommand(){
        System.out.print("\nQuestion id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print("\nType of vote(up/down): ");
        String voteType = scanner.nextLine().trim();

        voteManagementService.voteQuestion(user.getId(), id, voteType );
        System.out.print("\nChoose another option: ");

    }

    private void voteAnswerCommand() {
        System.out.print("\nAnswer id: ");
        Integer id = Integer.valueOf(scanner.nextLine().trim());

        System.out.print("\nType of vote(up/down): ");
        String voteType = scanner.nextLine().trim();

        voteManagementService.voteAnswer(user.getId(), id, voteType);
        System.out.print("\nChoose another option: ");

    }

}
