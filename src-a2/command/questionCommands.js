import questionModel from "../model/questionModel";

class AddQuestionCommand {
    constructor(title, text, tags) {
        this.title = title;
        this.text = text;
        this.tags = tags;
    }

    execute() {
        return questionModel.addQuestion(this.title, this.text, this.tags).then(question => {
            this.questionId = question.id;
            return question;
        });
    }

    undo() {
        return questionModel.deleteQuestion(this.questionId);
    }
}

class UpdateQuestionCommand {
    constructor(questionId, newTitle, newText, newTags) {
        this.questionId = questionId;
        this.newTitle = newTitle;
        this.newText = newText;
        this.newTags = newTags;
        this.lastQuestion = {...questionModel.getQuestion(questionId)};
    }

    execute() {
        return questionModel.updateQuestion(this.questionId, this.newTitle, this.newText, this.newTags);
    }

    undo() {
        return questionModel.updateQuestion(this.questionId, this.lastQuestion.title, this.lastQuestion.text, this.lastQuestion.tags);
    }
}


class DeleteQuestionCommand {
    constructor(questionId) {
        this.questionId = questionId;
        this.deletedQuestion = {...questionModel.getQuestion(questionId)};
    }

    execute() {
        return questionModel.deleteQuestion(this.questionId);
    }

    undo() {
        return questionModel.addQuestion(this.deletedQuestion.title, this.deletedQuestion.text, this.deletedQuestion.tags).then(q => {
            this.questionId = q.id;
            return q;
        });
    }
}

class VoteQuestionCommand {
    constructor(questionId, voteValue) {
        this.questionId = questionId;
        this.voteValue = voteValue;
    }

    execute() {
        return questionModel.voteQuestion(this.questionId, this.voteValue);
    }

    undo() {
        return questionModel.voteQuestion(this.questionId, -this.voteValue);
    }
}

export {AddQuestionCommand, DeleteQuestionCommand, UpdateQuestionCommand, VoteQuestionCommand};