import React, {Component} from "react"
import LoginRegister from "../dumb/LoginRegister"
import userPresenter from "../../presenter/userPresenter"
import userModel from "../../model/userModel"

const mapModelStateToComponentState = (modelState) => ({
    users:modelState.users,
    newUser:modelState.newUser
});


export default class SmartLoginRegister extends Component{
    constructor(){
        super();
        this.state = mapModelStateToComponentState(userModel.state);
        this.listener = modelState => this.setState(mapModelStateToComponentState(modelState));
        userModel.addListener("change", this.listener);
    }

    componentWillUnmount() {
        userModel.removeListener("change", this.listener);
    }

    render() {
        return (
            <LoginRegister
                onLogin = {userPresenter.onLogin}
                onRegister = {userPresenter.onRegister}
                onChange = {userPresenter.onChange}
                newUser = {this.state.newUser}
            />
        );
    }
}
