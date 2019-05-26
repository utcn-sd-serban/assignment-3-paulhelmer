import React from 'react'

const QuestionDetails = ({question, onViewAnswers}) => (
<div>
<article class="message is-warning">
  <div class="message-header">
    <p>{question.title}</p>
    <a class="button is-info is-rounded is-hovered" onClick = {onViewAnswers}>View Answers</a>
  </div>
  <div class="message-body">
  {question.text}
  <br/>
  {question.creationDate}
    </div>
</article>
</div>
);

export default QuestionDetails;
