import React from 'react'

const QuestionDetails = ({question, onViewAnswers, onEditQuestion, onDeleteQuestion, onUpVote, onDownVote}) => (
<div>
<article class="message is-warning">
  <div class="message-header">
    <p>{question.title}</p>
    
   <div class="buttons has-addons">
   <span class="button">Votes: {question.voteCount}</span>
  <span class="button is-success " onClick = {()=>onUpVote(question.id)}>+</span>
  <span class="button is-danger" onClick = {()=>onDownVote(question.id)}>-</span>
  </div>
  </div>
  <div class="message-body">
  {question.text}
  <br/>
  {question.creationDate}
  <br/>
    Author: {question.author.username} ({question.author.score} points)
  <div class="buttons is-centered">
     <span class="button is-info is-rounded is-hovered" onClick = {()=>onViewAnswers(question.id)}> <b>View Answers</b></span>
    <span class="button is-info is-rounded is-hovered" onClick = {()=>onEditQuestion(question.id)}> <b>Edit</b></span>
  <span class="button is-info is-rounded is-hovered" onClick = {()=>onDeleteQuestion(question.id)}> <b>Delete</b></span>
  
</div>

    </div>
</article>
</div>
);

export default QuestionDetails;
