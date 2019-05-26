import React from "react";
import {HashRouter, Route, Switch } from "react-router-dom";
import SmartQuestionList from "./view/smart/SmartQuestionList";
import LoginRegister from "./view/smart/SmartLoginRegister";
import SmartNewQuestion from "./view/smart/SmartNewQuestion";
import SmartFilteredQuestionList from "./view/smart/SmartFilteredQuestionList";


export default () => (
  <HashRouter>
    <Switch>
      <Route path="/" exact component={LoginRegister}/>
      <Route path="/questions" exact component={SmartQuestionList}/>
      <Route path="/new-question" exact component={SmartNewQuestion}/>
      <Route path="/filtered-questions" exact component={SmartFilteredQuestionList}/>
    </Switch>
  </HashRouter>);