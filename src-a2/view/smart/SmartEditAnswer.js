import React, {Component} from "react"
import EditAnswer from "../dumb/EditAnswer"
import answerModel from "../../model/answerModel"
import editAnswerPresenter from "../../presenter/editAnswerPresenter"

const mapModelStateToComponentState = (modelState) => ({
    answers:modelState.answers,
    updateAnswer:modelState.updateAnswer
});


export default class SmartEditAnswer extends Component{
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
            <EditAnswer
                onUpdate = {editAnswerPresenter.onUpdate}
                onChange = {editAnswerPresenter.onChange}
                updateAnswer = {this.state.updateAnswer}
                onUndo = {editAnswerPresenter.onUndo}
            onRedo = {editAnswerPresenter.onRedo}
            />
        );
    }
}