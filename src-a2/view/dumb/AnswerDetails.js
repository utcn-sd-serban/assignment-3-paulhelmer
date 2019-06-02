import React from 'react'

const AnswerDetails = ({answer, onEditAnswer, onDeleteAnswer, onUpVote, onDownVote}) => (
<div>
<article class="message is-warning">
  <div class="message-header">
    <p>{answer.id}</p>
       <div class="buttons has-addons">
   <span class="button">Votes: {answer.voteCount}</span>
  <span class="button is-success " onClick = {()=>onUpVote(answer.id)}>+</span>
  <span class="button is-danger" onClick = {()=>onDownVote(answer.id)}>-</span>
  </div>
  </div>
  <div class="message-body">
  {answer.text}
  <br/>
  {answer.creationDate}
  <br/>
    Author: {answer.author.username} ({answer.author.score} points)
    <div class="buttons is-centered">
    <span class="button is-info is-rounded is-hovered" onClick = {()=>onEditAnswer(answer.id)}> <b>Edit</b></span>
  <span class="button is-info is-rounded is-hovered" onClick = {()=>onDeleteAnswer(answer.id)}> <b>Delete</b></span>
  
</div>
    </div>
</article>
</div>
);

export default AnswerDetails;
