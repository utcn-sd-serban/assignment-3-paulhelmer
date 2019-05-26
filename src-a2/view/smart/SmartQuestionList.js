import React, {Component} from "react"
import questionListPresenter from "../../presenter/questionListPresenter"
import questionModel from "../../model/questionModel"
import QuestionList from "../dumb/QuestionList"

const mapModelStateToComponentState = (modelState) => ({
    questions: modelState.questions,
    newQuestion: modelState.newQuestion,
})

export default class SmartQuestionList extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(questionModel.state);
        this.state.questions.sort( (q1, q2) => (
            q1.creationDate<  q2.creationDate ? 1 : q1.creationDate > q2.creationDate ? -1 : 0
        ))
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        questionModel.addListener("change",this.listener);

    }

    ccomponentWillUnmount() {
        questionModel.removeListener("change", this.listener);
    }

    render() {
        return (
            <QuestionList title="Questions"
            onNewQuestion={questionListPresenter.onNewQuestion}
            questions= {this.state.questions}
            onViewAnswers = {questionListPresenter.onViewAnswers}
            onFilterTitle = {questionListPresenter.onFilterTitle}
            onFilterTag = {questionListPresenter.onFilterTag}
            filterText = {this.state.filterText}
            onSearchBarChange = {questionListPresenter.onSearchBarChange}
            />
        )
    }
}