import answerModel from "../model/answerModel.js"

import {
    AddAnswerCommand,
    DeleteAnswerCommand,
    UpdateAnswerCommand,
    VoteAnswerCommand
} from "../command/answerCommands";
import invoker from "../command/invoker"

class AnswerListPresenter {

    onChangeNewAnswerProperty(property,value) {
        answerModel.changeNewAnswerProperty(property,value);
    }
    onCreate(){
        var answer = answerModel.state.newAnswer;
        invoker.invoke(new AddAnswerCommand(answer.text, answer.questionId))
        answerModel.changeNewAnswerProperty("text", "");
        window.location.assign("#/answers"); 
    }

    onNewAnswer(){
        window.location.assign("#/new-answer");
    }


    onDelete(answerId) {
        var answer = answerModel.state.newAnswer;
        invoker.invoke(new DeleteAnswerCommand(answerId, answer.questionId));
        window.location.assign("#/answers/"+answer.questionId);


    }

    onEdit(answerId) {
        answerModel.prepareAnswerForUpdate(answerId);
        window.location.assign("#/edit-answer/" + answerId);
    }

    onUpVote(answerId) {
        var answer = answerModel.state.newAnswer;
        invoker.invoke(new VoteAnswerCommand(answerId, 1, answer.questionId));
        window.location.assign("#/answers/"+answer.questionId);

    }
    onDownVote(answerId) {
        var answer = answerModel.state.newAnswer;
        invoker.invoke(new VoteAnswerCommand(answerId, -1, answer.questionId));
        window.location.assign("#/answers/"+answer.questionId);

    }

    onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }

}

const answerListPresenter = new AnswerListPresenter();

export default answerListPresenter;