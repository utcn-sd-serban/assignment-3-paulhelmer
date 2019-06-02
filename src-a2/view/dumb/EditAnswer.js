import React from 'react';

const EditAnswer = ({
                    updateAnswer, onUpdate, onChange, onUndo, onRedo
                }) => (
                    <div className="hero is-success is-fullheight">
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
    <div class="container has-text-centered">
      <h1 class="title">
        Update an answer!
      </h1>
      <h2 class="subtitle">
        Fill in the information bellow.
      </h2>
     <div class = "field">
          <input class = "input is-warning is-rounded" type = "text" onChange = {e=>onChange("text", e.target.value)} value = {updateAnswer.text} placeholder = "Text"/>
     </div>
     <a class="button is-warning is-rounded is-hovered is-medium" onClick = {()=>onUpdate(updateAnswer.id)}>Update</a>   
    </div>
  </div>
  
</div>);

export default EditAnswer;