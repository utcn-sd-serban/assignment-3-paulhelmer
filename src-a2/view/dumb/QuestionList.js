import React from 'react';
import QuestionDetails from "./QuestionDetails"

const QuestionList = ({
                    questions, onViewAnswers, onNewQuestion, filterMode,
                    onFilterTitle, onFilterTag, onSearchBarChange, filterText
                }) => (
                    <div className="hero is-info is-fullheight">
  <div class="hero-head">
    <nav class="tabs is-boxed">
      <div class="container">
        <ul>
          <li class="is-active"><a>Home</a></li>
          <li><a>Questions</a></li>
        </ul>
      </div>
    </nav>
  </div>

  <div class="hero-body">
    <div class="container">
<div class="button is-warning is-rounded is-hovered is-medium" onClick ={onNewQuestion}>New Question</div>
<br/>
<br/>
<div class = "field">
          <input class = "input is-warning is-rounded" type = "text" onChange = {e=>onSearchBarChange(e.target.value)} value = {filterText} placeholder = "Search"/>
</div>
<div class="button is-warning is-rounded is-hovered is-medium" onClick ={onFilterTitle}>By Title</div>
<div class="button is-warning is-rounded is-hovered is-medium" onClick ={onFilterTag}>By Tag</div>
<br/>
<br/>
    <div class="container has-text-centered">
    {
        questions.map((question) => (
            <QuestionDetails
                question = {question}
                onViewAnswers = {onViewAnswers}
            />
        ))
    }
    </div>
</div>
</div>
  
</div>);

export default QuestionList;