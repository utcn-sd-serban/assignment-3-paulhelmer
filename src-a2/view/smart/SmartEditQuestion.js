import React, {Component} from "react"
import EditQuestion from "../dumb/EditQuestion"
import questionModel from "../../model/questionModel"
import editQuestionPresenter from "../../presenter/editQuestionPresenter"

const mapModelStateToComponentState = (modelState) => ({
    questions:modelState.questions,
    updateQuestion:modelState.updateQuestion
});


export default class SmartEditQuestion extends Component{
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
            <EditQuestion
                onUpdate = {editQuestionPresenter.onUpdate}
                onChange = {editQuestionPresenter.onChange}
                updateQuestion = {this.state.updateQuestion}
                onUndo = {editQuestionPresenter.onUndo}
            onRedo = {editQuestionPresenter.onRedo}
            />
        );
    }
}