import questionModel from "../model/questionModel.js"
import answerModel from "../model/answerModel.js"

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
        questionModel.loadFilteredByTitle(questionModel.state.filterText);
        window.location.assign("#/filtered-questions");
    }

    onFilterTag() {
        questionModel.loadFilteredByTags(questionModel.state.filterText);        
        window.location.assign("#/filtered-questions");
    }

    onChangeNewQuestionProperty(property,value) {
        questionModel.changeNewQuestionProperty(property,value);
    }
    onCreate(){
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
        answerModel.state.newAnswer.questionId=questionId;
        answerModel.loadAnswersForQuestion(questionId).then(
            ()=>window.location.assign("#/answers/"+questionId)
        );
    }

    

    onDelete(questionId) {
        window.location.assign('#/questions/');
        invoker.invoke(new DeleteQuestionCommand(questionId));
    }

    onEdit(questionId) {
        questionModel.prepareQuestionForUpdate(questionId);
        window.location.assign("#/edit-question/" + questionId);
    }

    onUpVote(questionId) {
        invoker.invoke(new VoteQuestionCommand(questionId, +1));
    }
    onDownVote(questionId) {
        invoker.invoke(new VoteQuestionCommand(questionId, -1));
    }

    onInitAllQuestions() {
        questionModel.loadAllQuestions();
    }

    onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }
}

const questionListPresenter = new QuestionListPresenter();

export default questionListPresenter;