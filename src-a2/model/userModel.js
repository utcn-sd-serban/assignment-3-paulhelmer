import {EventEmitter} from "events";
import WebSocketListener from "../ws/WebSocketListener"
import RestClientFactory from "../rest/RestClientFactory"

const makeUser = (id, username, password, score, isModerator, isBanned) =>
({
    id, username, password, score, isModerator, isBanned
})
class User extends EventEmitter{
    constructor() {
        super();
        this.state = {
            users: [],

            newUser: {
                username:"",
                password:""
            },
            loggedInUser: null
        };
    }


    banUserToLocalState(bannedUserId) {
        var user = this.state.users.find(u => u.id === bannedUserId);
        if (user) user.isBanned = true;

        if (this.state.loggedInUser.id === bannedUserId) this.isBanned= true;
        this.emit("change", this.state);
    }
    addUser(username, password){
        this.state = {
            ...this.state,
            users: this.state.users.concat([{
                username: username,
                password: password
            }])
        };
        this.emit("change", this.state);
    }

    changeNewUserProperty(property, value) {
        this.state = {
            ...this.state,
            newUser: {
                ...this.state.newUser,
                [property]: value 
            }
        };
        this.emit("change", this.state);
    }

    changeLoggedInUserProperty(property, value){
        this.state = {
            ...this.state,
            loggedInUser: {
                ...this.state.loggedInUser,
                [property] : value
            }
        };
        this.emit("change", this.state);
    }

    login(username, password){

        this.client = new RestClientFactory(username, password);
        return this.client.createLoginClient().loadCurrentLoggedUser()
            .then(user=>
            {
                if(!user) return false;
                if(this.hasOwnProperty("listener")){
                    delete this.listener.client.deactivate();
                }
                this.listener = new WebSocketListener(username, password);
                this.listener.on("event", event => webSocketListener(event));
                this.state = {
                    ...this.state,
                    loggedInUser:user
                };
            this.emit("change", this.state);
            return true;
            });
    }

    loadAllUsers() {
      return this.client.createUserClient().loadAllUsers()
            .then(users => {
                    this.state = {
                        ...this.state,
                        users
                    }

                    this.emit("change", this.state);
                }
            );  
    }

    banUser(bannedUserId){
        return this.client.createUserClient.banUser(bannedUserId);
    }
}

const userModel = new User();

function webSocketListener(event){
    if(event.type === "USER_BANNED")
        userModel.banUserToLocalState(event.userId);
}
export default userModel;