import React, {Component} from "react"
import NewQuestion from "../dumb/NewQuestion"
import questionModel from "../../model/questionModel"
import newQuestionPresenter from "../../presenter/newQuestionPresenter"

const mapModelStateToComponentState = (modelState) => ({
    questions:modelState.questions,
    newQuestion:modelState.newQuestion
});


export default class SmartNewQuestion extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(questionModel.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        questionModel.addListener("change", this.listener);
    }

    componentWillUnmount() {
        questionModel.removeListener("change", this.listener);
    }

    render() {
        return (
            <NewQuestion
                onCreate = {newQuestionPresenter.onCreate}
                onChange = {newQuestionPresenter.onChange}
                newQuestion = {this.state.newQuestion}
            />
        );
    }
}