import React, {Component} from "react"
import answerListPresenter from "../../presenter/answerListPresenter"
import answerModel from "../../model/answerModel"
import AnswerList from "../dumb/AnswerList"

const mapModelStateToComponentState = (modelState) => ({
    answers: modelState.answers,
    newAnswer: modelState.newAnswer,
})

export default class SmartAnswerList extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(answerModel.state);
        this.state.answers.sort( (q1, q2) => (
            q1.voteCount<  q2.voteCount ? 1 : q1.voteCount > q2.voteCount ? -1 : 0
        ))
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        answerModel.addListener("change",this.listener);

    }

    ccomponentWillUnmount() {
        answerModel.removeListener("change", this.listener);
    }

    render() {
        return (
            <AnswerList title="Answers"
            onNewAnswer={answerListPresenter.onNewAnswer}
            onEditAnswer={answerListPresenter.onEdit}
            onDeleteAnswer={answerListPresenter.onDelete}
            answers= {this.state.answers}
            onUpVote={answerListPresenter.onUpVote}
            onDownVote={answerListPresenter.onDownVote}
            onUndo = {answerListPresenter.onUndo}
            onRedo = {answerListPresenter.onRedo}

            />
        )
    }
}