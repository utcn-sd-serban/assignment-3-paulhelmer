import userModel from "../model/userModel";
import model from "../model/model";

class UserPresenter {
    onLogin = () => {
        let newUser = userModel.state.newUser;
        let loginSuccess = model.makeLogin(newUser.username, newUser.password);

        userModel.changeNewUserProperty("username", "");
        userModel.changeNewUserProperty("password", "");

        if (loginSuccess) {
            userModel.changeLoggedInUserProperty("loggedInUser", newUser.username);
            window.location.assign("#/questions");
        } else {
            window.location.assign("#/");
        }
    };

    onRegister = () => {
        let newUser = userModel.state.newUser;

        userModel.addUser(newUser.username, newUser.password);
        userModel.changeNewUserProperty("username", "");
        userModel.changeNewUserProperty("password", "");
        userModel.changeLoggedInUserProperty("loggedInUser", newUser.username);
        window.location.assign("#/questions");
    };

    onChange = (property, value) => {
        userModel.changeNewUserProperty(property, value);
    };
}

const userPresenter = new UserPresenter();

export default userPresenter;