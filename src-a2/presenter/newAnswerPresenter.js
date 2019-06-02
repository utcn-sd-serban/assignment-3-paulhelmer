import answerModel from "../model/answerModel.js"
import userModel from "../model/userModel.js";

import {
    AddAnswerCommand,
    DeleteAnswerCommand,
    UpdateAnswerCommand,
    VoteAnswerCommand
} from "../command/answerCommands";
import invoker from "../command/invoker"

class NewAnswerPresenter {

    onChange = (property,value) => {
        answerModel.changeNewAnswerProperty(property,value);
    }

    onCreate = () => {

        let newAnswer = answerModel.state.newAnswer;

        invoker.invoke(new AddAnswerCommand(newAnswer.text, newAnswer.questionId));

        answerModel.changeNewAnswerProperty("text", "");

        window.location.assign("#/answers/"+newAnswer.questionId);
    };
    onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }

}

const newAnswerPresenter = new NewAnswerPresenter();

export default newAnswerPresenter;