import {EventEmitter} from "events";
import WebSocketListener from "../ws/WebSocketListener";
import RestClientFactory from "../rest/RestClientFactory";
import answerModel from "../model/answerModel.js"
import questionModel from "../model/questionModel.js"
import userModel from "../model/userModel.js"


const makeUser = (id, username, password, score, isModerator, isBanned) => ({
    id, username, password, score, isModerator, isBanned
})

const makeQuestion = (id, author, title, text, creationDate, tags, voteCount) => ({
    id, author, title, text, creationDate, tags, voteCount
})

const makeAnswer = (id, author, text, creationDate, questionId, voteCount) => ({
    id, author, text, creationDate, questionId, voteCount
})

class Model extends EventEmitter {
    constructor() {
        super();

        this.state = {
            users: [],

            currentUser: null,

            newUser: {
                username: "",
                password: ""
            },

            questions: [],

            newQuestion: {
                title: "",
                text: "",
                tagsAsString: ""
            },

            updateQuestion: {
                title: "",
                text: "",
            },

            tags: [],

            filterText: "",

            answers: [],

            newAnswer: {text: ""},

            updateAnswer: {text: ""}
        };
    }


    makeLogin(username, password) {
        this.client = new RestClientFactory(username, password);
        return this.client.createLoginClient().loadCurrentLoggedUser().then(user => {
            if (!user) return false;

            if (this.hasOwnProperty("listener")) {
                delete this.listener.client.deactivate();
            }
            this.listener = new WebSocketListener(username, password);
            this.listener.on("event", event => webSocketListener(event));
            this.state = {
                ...this.state,
                currentUser: user
            };

            this.emit("change", this.state);

            return true;
        });
    }

    changeLoginProperty(property, value) {
        this.state = {
            ...this.state,
            loginUser: {
                ...this.state.loginUser,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    updateUserInfo(newUserData) {
        this.state = {
            ...this.state,
            users: this.state.users.map(u => u.id === newUserData.id ? {
                ...u,
                score: newUserData.score,
                username: newUserData.username
            } : {...u}),

            questions: this.state.questions.map(q => q.author.id === newUserData.id ? {
                ...q,
                author: {...newUserData}
            } : {...q}),

            answers: this.state.answers.map(a => a.author.id === newUserData.id ? {
                ...a,
                author: {...newUserData}
            } : {...a}),

            currentUser: this.state.currentUser.id === newUserData.id ? {
                ...this.state.currentUser,
                score: newUserData.score
            } : this.state.currentUser
        };

        this.emit("change", this.state);
    }
}

const model = new Model();

function webSocketListener(event) {
    {
        switch (event.type) {
            case "ANSWER_CREATED":
                answerModel.addAnswerToLocalState(event.answerDTO);
                break;
            case "ANSWER_DELETED":
                answerModel.deleteAnswerToLocalState(event.answerId);
                break;
            case "ANSWER_UPDATED":
                answerModel.updateAnswerTextToLocalState(event.answerDTO.id, event.answerDTO.text);
                break;
            case "QUESTION_CREATED":
                questionModel.addQuestionToLocalState(event.questionDTO);
                break;
            case "QUESTION_DELETED":
                questionModel.deleteQuestionToLocalState(event.questionId);
                break;
            case "QUESTION_UPDATED":
                questionModel.updateQuestionToLocalState(event.questionDTO.id, event.questionDTO.title, event.questionDTO.text);
                break;
            case "USER_UPDATED":
                userModel.updateUserInfo(event.userDTO);
                break;
            case "VOTED_ANSWER":
                answerModel.sendVoteAnswerToLocalState(event.answerDTO);
                break;
            case "VOTED_QUESTION":
                questionModel.sendVoteQuestionToLocalState(event.questionDTO);
                break;
            case "USER_BANNED":
                userModel.banUserToLocalState(event.userId);
                break;
        }
    }
}

export default model;