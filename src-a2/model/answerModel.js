import model, {makeAnswer} from "../model/model";
import {EventEmitter} from "events";

class AnswerModel extends EventEmitter {

    constructor() {
        super();

        this.state = {

            currentUser: null,

            answers: [],

            newAnswer: {text: ""},

            updateAnswer: {text: ""}
        };
    }


    changeStateProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }

    changeNewAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            newAnswer: {
                ...this.state.newAnswer,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

        changeUpdateAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            updateAnswer: {
                ...this.state.updateAnswer,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    loadAnswersForQuestion(questionId) {
        return model.client.createAnswerClient().loadAnswersForQuestion(questionId)
            .then(answers => {
                    this.state = {
                        ...this.state,
                        answers:answers
                    }

                    this.emit("change", this.state);
                }
            );
    }

    prepareAnswerForUpdate(answerId) {
        var answer = this.getAnswer(answerId);
        this.state.updateAnswer.text = answer.text;
        this.state.updateAnswer.id = answerId;

        this.emit("change", this.state);
    }
    getAnswer(answerId) {
        return this.state.answers.find(a => a.id === answerId);
    }

    addAnswer(text, questionId) {
        return model.client.createAnswerClient().createAnswer(text, questionId);
    }

    updateAnswer(answerId, newText, questionId) {
        return model.client.createAnswerClient().updateAnswer(answerId, newText);
    }

    deleteAnswer(answerId, questionId) {
        return model.client.createAnswerClient().deleteAnswer(answerId, questionId);
    }

    voteAnswer(answerId, voteValue, questionId) {
        return model.client.createAnswerClient().voteAnswer(answerId, voteValue).then(()=>this.loadAnswersForQuestion(questionId));;
    }

    sendVoteAnswerToLocalState(votedAnswer) {
        var answer = this.getAnswer(votedAnswer.id);
        if(answer === undefined) return;
        answer.voteCount = votedAnswer.voteCount;

        this.emit("change", this.state);
    }

    addAnswerToLocalState(answer) {
        this.state = {
            ...this.state,
            answers: [{...answer}].concat(this.state.answers)
        };

        this.emit("change", this.state);
    }

    updateAnswerTextToLocalState(answerId, newText) {
        var answer = this.getAnswer(answerId);
        if (answer === undefined) {
            return;
        }

        answer.text = newText;

        this.emit("change", this.state);
    }

    deleteAnswerToLocalState(answerId) {
        this.state = {
            ...this.state,
            answers: this.state.answers.filter(a => a.id !== answerId),
        };

        this.emit("change", this.state);
    }
}

const answerModel = new AnswerModel();


export default answerModel;