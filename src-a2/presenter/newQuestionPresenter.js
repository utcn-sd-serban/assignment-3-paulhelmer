import questionModel from "../model/questionModel.js"
import userModel from "../model/userModel.js";
import invoker from "../command/invoker"

class NewQuestionPresenter {

    onChange = (property,value) => {
        questionModel.changeNewQuestionProperty(property,value);
    }

    onCreate = () => {

        let newQuestion = questionModel.state.newQuestion;
        let currentUser = userModel.state.loggedInUser;
        var tags = newQuestion.tags.trim().split(',');
        //invoker.invoke(new AddQuestionCommand(newQuestion.title, neqQuestion.text, tags));


        questionModel.addQuestion(newQuestion.title,newQuestion.text, tags);
        questionModel.changeNewQuestionProperty("title", "");
        questionModel.changeNewQuestionProperty("text", "");
        questionModel.changeNewQuestionProperty("tags", "");
        window.location.assign("#/questions");
    };

     onUndo(){
        invoker.undo();
    }
    onRedo(){
        invoker.redo();
    }

}

const newQuestionPresenter = new NewQuestionPresenter();

export default newQuestionPresenter;