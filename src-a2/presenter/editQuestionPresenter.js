import questionModel from "../model/questionModel.js"
import userModel from "../model/userModel.js";

import {
    AddQuestionCommand,
    DeleteQuestionCommand,
    UpdateQuestionCommand,
    VoteQuestionCommand
} from "../command/questionCommands";
import invoker from "../command/invoker"
class EditQuestionPresenter {

    onChange = (property,value) => {
        questionModel.changeUpdateQuestionProperty(property,value);
    }

    onUpdate(questionId) {
        var tags = questionModel.state.updateQuestion.tags.trim().split(',');
        invoker.invoke(new UpdateQuestionCommand(questionId, questionModel.state.updateQuestion.title, questionModel.state.updateQuestion.text, tags));
        questionModel.changeUpdateQuestionProperty("title", "");
        questionModel.changeUpdateQuestionProperty("text", "");
        questionModel.changeUpdateQuestionProperty("tags", "");
        window.location.assign("#/questions");
    }
        onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }

}

const editQuestionPresenter = new EditQuestionPresenter();

export default editQuestionPresenter;