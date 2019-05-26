import React, {Component} from "react"
import questionListPresenter from "../../presenter/questionListPresenter"
import questionModel from "../../model/questionModel"
import QuestionList from "../dumb/QuestionList"

const mapModelStateToComponentState = (modelState) => ({
    filteredQuestions: modelState.filteredQuestions,
    newQuestion: modelState.newQuestion,
})

export default class SmartFilteredQuestionList extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(questionModel.state);
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
            questions= {this.state.filteredQuestions}
            onViewAnswers = {questionListPresenter.onViewAnswers}
            onFilterTitle = {questionListPresenter.onFilterTitle}
            onFilterTag = {questionListPresenter.onFilterTag}
            filterText = {this.state.filterText}
            onSearchBarChange = {questionListPresenter.onSearchBarChange}
            />
        )
    }
}