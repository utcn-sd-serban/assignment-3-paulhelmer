import answerModel from "../model/answerModel";

class AddAnswerCommand {
    constructor(text, questionId) {
        this.text = text;
        this.questionId = questionId;
    }

    execute() {
        return answerModel.addAnswer(this.text, this.questionId);
    }

    undo() {
        return answerModel.deleteAnswer(this.answerId);
    }
}

class UpdateAnswerCommand {
    constructor(answerId, newText, questionId) {
        this.answerId = answerId;
        this.newText = newText;
        this.lastAnswer = {...answerModel.getAnswer(answerId)};
        this.questionId=questionId;
    }

    execute() {
        return answerModel.updateAnswer(this.answerId, this.newText, this.questionId);
    }

    undo() {
        return answerModel.updateAnswer(this.answerId, this.lastAnswer.text, this.questionId);
    }
}

class DeleteAnswerCommand {
    constructor(answerId, questionId) {
        this.answerId = answerId;
        this.deletedAnswer = {...answerModel.getAnswer(answerId)};
        this.questionId = questionId;
    }

    execute() {
        return answerModel.deleteAnswer(this.answerId, this.questionId);
    }

    undo() {
        return answerModel.addAnswer(this.deletedAnswer.text, this.deletedAnswer.questionId).then(ans => {
            this.answerId = ans.id;
            return ans;
        });
    }
}

class VoteAnswerCommand {
    constructor(answerId, voteValue, questionId) {
        this.answerId = answerId;
        this.voteValue = voteValue;
        this.questionId = questionId
    }

    execute() {
        return answerModel.voteAnswer(this.answerId, this.voteValue, this.questionId);
    }

    undo() {
        return answerModel.voteAnswer(this.answerId, -this.voteValue, this.questionId);
    }
}

export {AddAnswerCommand, DeleteAnswerCommand, UpdateAnswerCommand, VoteAnswerCommand};