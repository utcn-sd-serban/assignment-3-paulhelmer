import answerModel from "../model/answerModel.js"
import userModel from "../model/userModel.js";

import {
    AddAnswerCommand,
    DeleteAnswerCommand,
    UpdateAnswerCommand,
    VoteAnswerCommand
} from "../command/answerCommands";
import invoker from "../command/invoker"
class EditAnswerPresenter {

    onChange = (property,value) => {
        answerModel.changeUpdateAnswerProperty(property,value);
    }

    onUpdate(answerId) {
        invoker.invoke(new UpdateAnswerCommand(answerId, answerModel.state.updateAnswer.text,answerModel.state.newAnswer.questionId ));
        answerModel.changeUpdateAnswerProperty("text", "");
        window.location.assign("#/answers/"+answerModel.state.newAnswer.questionId);
    }
        onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }

}

const editAnswerPresenter = new EditAnswerPresenter();

export default editAnswerPresenter;