package nnar.learning_app.ui.login

import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usecase.LoginUseCase

class LoginPresenter(private val view: LoginView, private val useCase: LoginUseCase) {

    internal fun verifyUsername(username: String, hasFocus: Boolean){
        if(!hasFocus && username != ""){
            view.drawSuccessUsernameField()
        }
    }

    internal fun verifyPass(pass:String, hasFocus: Boolean){
        if(!hasFocus && pass != ""){
            view.drawSuccessPasswordField()
        }
    }

    internal fun verifyUser(user:String, pass: String) {
        val response: UserResponse = useCase.loginUser(user, pass)

        if (response.responseValue){
            view.drawSuccessUsernameField()
            view.drawSuccessPasswordField()
            view.showLoginResponse("Success Login")
            view.navigateToHome()
        }else{
            if(response.msg == "Wrong Password"){
                view.showErrorPassField("Wrong password")
            }else{
                view.showErrorNameField("Wrong username")
                view.showErrorPassField("Wrong password")
            }
        }
    }

}