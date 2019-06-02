import React from 'react';
import AnswerDetails from "./AnswerDetails"

const AnswerList = ({
                    answers, onNewAnswer, onEditAnswer, onDeleteAnswer, onUpVote, onDownVote,onUndo, onRedo
                }) => (
                    <div className="hero is-info is-fullheight">
  <div class="hero-head">
    <nav class="tabs is-boxed">
      <div class="container">
        <ul>
          <li class="is-active"><a>Login</a></li>
          <li><a>Questions</a></li>
          <li onClick = {onUndo}><a>Undo</a></li>
          <li onClick = {onRedo}><a>Redo</a></li>
        </ul>
      </div>
    </nav>
  </div>

  <div class="hero-body">
    <div class="container">
<div class="button is-warning is-rounded is-hovered is-medium" onClick ={onNewAnswer}>New Answer</div>
<br/>
<br/>
    <div class="container has-text-centered">
    {
        answers.map((answer) => (
            <div>
            <AnswerDetails
                answer = {answer}
                onEditAnswer = {onEditAnswer}
                onDeleteAnswer = {onDeleteAnswer}
                onUpVote = {onUpVote}
                onDownVote = {onDownVote}
            />
            </div>
        ))
    }
    </div>
</div>
</div>
  
</div>);

export default AnswerList;