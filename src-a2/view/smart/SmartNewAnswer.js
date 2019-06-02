import React, {Component} from "react"
import NewAnswer from "../dumb/NewAnswer"
import answerModel from "../../model/answerModel"
import newAnswerPresenter from "../../presenter/newAnswerPresenter"

const mapModelStateToComponentState = (modelState) => ({
    answers:modelState.answers,
    newAnswer:modelState.newAnswer
});


export default class SmartNewAnswer extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(answerModel.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        answerModel.addListener("change", this.listener);
    }

    componentWillUnmount() {
        answerModel.removeListener("change", this.listener);
    }

    render() {
        return (
            <NewAnswer
                onCreate = {newAnswerPresenter.onCreate}
                onChange = {newAnswerPresenter.onChange}
                newAnswer = {this.state.newAnswer}
                onUndo = {newAnswerPresenter.onUndo}
                onRedo = {newAnswerPresenter.onRedo}
            />
        );
    }
}