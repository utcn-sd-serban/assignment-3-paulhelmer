import questionModel from "../model/questionModel.js"
import model from "../model/userModel.js";
//import answerModel from "../model/answerModel.js"

import {
    AddQuestionCommand,
    DeleteQuestionCommand,
    UpdateQuestionCommand,
    VoteQuestionCommand
} from "../command/questionCommands";
import invoker from "../command/invoker"

class QuestionListPresenter {
    onSearchBarChange(filterText) {
        questionModel.changeStateProperty("filterText",filterText);
    }

    onFilterTitle() {
        questionModel.changeStateProperty("filterMode", "title");
        questionModel.loadFilteredByTitle(questionModel.state.filterText);
        window.location.assign("#/filtered-questions");
    }

    onFilterTag() {
        questionModel.changeStateProperty("filterMode", "tag");
        questionModel.loadFilteredByTags(questionModel.state.filterText);        
        window.location.assign("#/filtered-questions");
    }

    onChangeNewQuestionProperty(property,value) {
        questionModel.changeNewQuestionProperty(property,value);
    }
    onCreate(){
        //questionModel.addQuestion(questionModel.state.newQuestion.title,questionModel.state.newQuestion.text,model.state.currentUser.userName, questionModel.state.newQuestion.tags);
        var question = questionModel.state.newQuestion;
        var tags = question.tags.trim().split(',');
        invoker.invoke(new AddQuestionCommand(question.title, question.text, tags))

        questionModel.changeNewQuestionProperty("title", "");
        questionModel.changeNewQuestionProperty("text", "");
        questionModel.changeNewQuestionProperty("tags", "");
        window.location.assign("#/questions"); 
    }

    onNewQuestion(){
        window.location.assign("#/new-question");
    }

    onViewAnswers(questionId) {
        /*answerModel.loadAnswersForQuestion(qustionId).then(
            ()=>window.location.assign("#/answers/"+questionId)
        );*/
    }

    onUpdate(questionId) {
        invoker.invoke(new UpdateQuestionCommand(questionId, questionModel.state.updateQuestion.title, model.questionState.updateQuestion.text));
        questionModel.changeUpdateQuestionProperty("title", "");
        questionModel.changeUpdateQuestionProperty("text", "");
        window.location.assign("#/questions");
    }

    onDelete(questionId) {
        window.location.assign('#/questions/')
        invoker.invoke(new DeleteQuestionCommand(questionId));
    }

    onEdit(questionId) {
        questionModel.prepareQuestionForUpdate(questionId);
        window.location.assign("#/edit-question/" + questionId);
    }

    onVote(questionId, vote) {
        invoker.invoke(new VoteQuestionCommand(questionId, vote));
    }

    onInitAllQuestions() {
        questionModel.loadAllQuestions();
    }

    onInitFilterByTitle(title) {
        questionModel.loadFilteredByTitle(title);
    }

    onInitFilterByTags(tags) {
        questionModel.loadFilteredByTags(tags);
    }

    onInitDetails(questionId) {
        //answerModel.loadAnswersForQuestion(questionId);
    }


}

const questionListPresenter = new QuestionListPresenter();

export default questionListPresenter;