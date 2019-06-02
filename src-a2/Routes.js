import React from "react";
import {HashRouter, Route, Switch } from "react-router-dom";
import SmartQuestionList from "./view/smart/SmartQuestionList";
import LoginRegister from "./view/smart/SmartLoginRegister";
import SmartNewQuestion from "./view/smart/SmartNewQuestion";
import SmartAnswerList from "./view/smart/SmartAnswerList";
import SmartEditQuestion from "./view/smart/SmartEditQuestion";
import SmartNewAnswer from "./view/smart/SmartNewAnswer";
import SmartEditAnswer from "./view/smart/SmartEditAnswer";



export default () => (
  <HashRouter>
    <Switch>
      <Route path="/" exact component={LoginRegister}/>
      <Route path="/questions" exact component={SmartQuestionList}/>
      <Route path="/new-question" exact component={SmartNewQuestion}/>
      <Route path="/filtered-questions" exact component={SmartQuestionList}/>
      <Route path="/answers/:questionId" exact component = {SmartAnswerList}/>
      <Route path="/edit-question/:questionId" exact component = {SmartEditQuestion}/>
      <Route path="/new-answer" exact component = {SmartNewAnswer}/>
      <Route path="/edit-answer/:answerId" exact component = {SmartEditAnswer}/>
    </Switch>
  </HashRouter>);