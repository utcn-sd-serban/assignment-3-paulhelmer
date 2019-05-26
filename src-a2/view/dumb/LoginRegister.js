import React from 'react';

const LoginRegister = ({
                    newUser, onLogin, onRegister, onChange
                }) => (
                    <div className="hero is-success is-fullheight">
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
    <div class="container has-text-centered">
      <h1 class="title">
        Welcome!
      </h1>
      <h2 class="subtitle">
        Please log in or register.
      </h2>
     <div class = "field">
          <input class = "input is-warning is-rounded" type = "text" onChange = {e=>onChange("username", e.target.value)} value = {newUser.username} placeholder = "Username"/>
          <input class = "input is-warning is-rounded" type = "password" onChange = {e=>onChange("password", e.target.value)} value = {newUser.password} placeholder = "Password"/>
     </div>
     <a class="button is-warning is-rounded is-hovered is-medium" onClick = {onLogin}>Log in</a>
     <a class="button is-warning is-rounded is-hovered is-medium" onClick = {onRegister}>Register</a>
   
    </div>
  </div>
  
</div>);

export default LoginRegister;