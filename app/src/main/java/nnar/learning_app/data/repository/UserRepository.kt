package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.model.UserResponse

class UserRepository {

    private val userSet: MutableSet<User> = mutableSetOf<User>(
        User("nicole", "nicole@gmail.com", "123456"),
        User("agus", "agus@gmail.com", "567890"),
        User("alberto", "alberto@gmail.com", "idiota")
    )

    fun loginUser(username: String, password: String): UserResponse {
        //var temp: Boolean = false
        var userResponse = UserResponse("", false, "")

        if (username != "" && password != "") {
            for (user in userSet) {
                if ((user.username == username) && (user.password == password)) {
                    userResponse = UserResponse(username, true, "Login Successful")
                } else if ((user.username == username) && (user.password != password)) {
                    userResponse = UserResponse(username, false, "Wrong Password")
                } else if ((user.username != username) && (user.password != password)) {
                    userResponse = UserResponse(username, false, "User No Registered")
                }
                if (userResponse.responseValue) {
                    break
                }
            }
        }
        return userResponse
    }

    fun registerUser(username: String, email: String, password: String): UserResponse{
        var userResponse = UserResponse("", false, "")
        if(username != "" && email != "" && password != ""){
            val userAux = User(username, email, password)
            userResponse = if (userSet.contains(userAux)){
                UserResponse(username, false, "User already Registered")
            }else{
                userSet.add(userAux)
                UserResponse(username, true, "Registration Successful")
            }
        }
        return userResponse
    }

    fun verifyUsername(username: String): UserResponse{
        var userResponse = UserResponse("", false, "")
        for (user in userSet){
            if (user.username == username){
                userResponse = UserResponse(username, true, "Username already exists")
            }else if (user.username != username){
                userResponse = UserResponse(username, false, "User No Registered")
            }
            if (userResponse.responseValue){
                break
            }
        }
        return userResponse
    }

    fun verifyEmail(email: String): UserResponse{
        var userResponse = UserResponse("", false, "")
        for (user in userSet){
            if (user.email == email){
                userResponse = UserResponse(email, true, "Username already exists")
            }else if (user.email != email){
                userResponse = UserResponse(email, false, "User No Registered")
            }
            if (userResponse.responseValue){
                break
            }
        }
        return userResponse
    }

}