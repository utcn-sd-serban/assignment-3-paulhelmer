import questionModel from "../model/questionModel.js"
import userModel from "../model/userModel.js";

class NewQuestionPresenter {

    onChange = (property,value) => {
        questionModel.changeNewQuestionProperty(property,value);
    }

    onCreate = () => {
        let newQuestion = questionModel.state.newQuestion;
        let currentUser = userModel.state.loggedInUser;

        questionModel.addQuestion(currentUser.userName, newQuestion.title,newQuestion.text, newQuestion.tags);
        questionModel.changeNewQuestionProperty("title", "");
        questionModel.changeNewQuestionProperty("text", "");
        questionModel.changeNewQuestionProperty("tags", "");
        window.location.assign("#/questions");
    };

}

const newQuestionPresenter = new NewQuestionPresenter();

export default newQuestionPresenter;