import model, {makeQuestion} from "../model/model";
import {EventEmitter} from "events";

class QuestionModel extends EventEmitter {

    constructor() {
        super();

        this.state = {

            currentUser: null,
            questions: [],

            newQuestion: {
                title: "",
                text: "",
                tagsAsString: ""
            },

            updateQuestion: {
                title: "",
                text: "",
                tags:""
            },

            tags: [],

            filterText: "",

        };
    }
    loadAllQuestions() {
        return model.client.createQuestionClient().loadAllQuestions()
            .then(questions => {
                    this.state = {
                        ...this.state,
                        questions: questions
                    }
                    this.emit("change", this.state);
                }
            );
    }

    changeStateProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }

    changeUpdateQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            updateQuestion: {
                ...this.state.updateQuestion,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }
        changeNewQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            newQuestion: {
                ...this.state.newQuestion,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    addQuestion(title, text, tags) {
        return model.client.createQuestionClient().createQuestion(title, text, tags).then(()=>this.loadAllQuestions());
    }

    updateQuestion(questionId, newTitle, newText, newTags) {
        return model.client.createQuestionClient().updateQuestion(questionId, newTitle, newText, newTags);
    }

    deleteQuestion(questionId) {
        return model.client.createQuestionClient().deleteQuestion(questionId);
    }

    voteQuestion(questionId, voteValue) {
        return model.client.createQuestionClient().voteQuestion(questionId, voteValue).then(()=>this.loadAllQuestions());
    }

    loadFilteredByTags(tagsAsString) {
        return model.client.createQuestionClient().loadFilteredByTags(tagsAsString).then(questions => {
                this.state = {
                    ...this.state,
                    questions:questions
                }

                this.emit("change", this.state);
            }
        );
    }

    loadFilteredByTitle(title) {
        return model.client.createQuestionClient().loadFilteredByTitle(title).then(questions => {
                this.state = {
                    ...this.state,
                    questions: questions
                }

                this.emit("change", this.state);
            }
        );
    }

    getQuestion(id) {
        var res = this.state.questions.find(q => q.id === id);
        return res;
    }

    prepareQuestionForUpdate(questionId) {
        var question = this.getQuestion(questionId);
        this.state.updateQuestion.title = question.title;
        this.state.updateQuestion.text = question.text;
        this.state.updateQuestion.id = questionId;
        this.state.updateQuestion.tags = question.tags;

        this.emit("change", this.state);
    }

    filterQuestionsByTagInLocalState(tags) {
        return this.state.questions.filter(q => tags.every(t => q.tags.includes(t)));
    }

    filterQuestionsByTitleInLocalState(title) {
        return this.state.questions.filter(q => q.title.includes(title));
    }

     addQuestionToLocalState(question) {
        this.state = {
            ...this.state,
            questions: [question].concat(this.state.questions)
        };

        this.emit("change", this.state);
    }

    updateQuestionToLocalState(questionId, newTitle, newText) {
        var question = this.getQuestion(questionId);
        if (question === undefined) {
            return;
        }

        question.title = newTitle;
        question.text = newText;

        this.emit("change", this.state);
    }

    deleteQuestionToLocalState(questionId) {
        this.state = {
            ...this.state,
            questions: this.state.questions.filter(q => q.id !== questionId),
        };
        this.emit("change", this.state);
    }

    sendVoteQuestionToLocalState(votedQuestion) {
        var question = this.getQuestion(votedQuestion.id);
        if(question === undefined) return;
        question.voteCount = votedQuestion.voteCount;

        this.emit("change", this.state);
    }

}

const questionModel = new QuestionModel();

export default questionModel;