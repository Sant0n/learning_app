package nnar.learning_app.ui.register

import android.util.Patterns

import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usercase.RegisterUseCase

class RegisterPresenter(private val view: RegisterView, private val useCase: RegisterUseCase) {

    internal fun verifyUsername(username: String, hasFocus: Boolean, isNotBlank: Boolean){
        if(!hasFocus && isNotBlank){
            val response: UserResponse = useCase.verifyUsername(username)

            if(response.responseValue){
                view.showRegisterResponse("Try another username")
                view.showErrorNameField(response.msg)

            }else{
                view.drawSuccessUsernameField()
            }
        }
    }

    internal fun verifyEmail(email: String, hasFocus: Boolean, isNotBlank: Boolean){
        if(!hasFocus && isNotBlank) {
            val response: UserResponse = useCase.verifyEmail(email)
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !response.responseValue) {
                view.drawSuccessEmailField()
            } else {
                view.showErrorEmailField("Wrong email format")
                view.showRegisterResponse("Wrong email format")
            }
        }
    }

    internal fun verifyPass(pass: String, repeatPass: String, hasFocus: Boolean, passBlank: Boolean, repeatPassBlank: Boolean){
        if(!hasFocus && (passBlank && repeatPassBlank)){
            if (pass == repeatPass){
                 view.drawSuccessPasswordField()
                 view.drawSuccessRepeatPasswordField()
            }else{
                 view.showRegisterResponse("Passwords don't coincide")
                 view.showErrorPassField("Wrong password")
                 view.showErrorRepeatPassField("Wrong password")
            }
        }
    }

    internal fun registerNewUser(user: String, email:String, pass: String, repeatPass: String){
        if(pass == repeatPass){
            val response: UserResponse = useCase.registerUser(user, email, pass)

            if(response.responseValue){
                view.showRegisterResponse("Success Register")
                view.navigateToHome()
            }else{
                view.showRegisterResponse("Error in register")
                view.showErrorNameField("Wrong username")
                view.showErrorEmailField("Wrong email")
                view.showErrorPassField("Wrong password")
                view.showErrorRepeatPassField("Wrong password")
            }
        }
    }

}