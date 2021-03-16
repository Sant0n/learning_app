package nnar.learning_app.ui.register

import android.util.Patterns

import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usercase.RegisterUseCase

class RegisterPresenter(private val view: RegisterView, private val useCase: RegisterUseCase) {

    internal fun verifyUsername(username: String){
        val response: UserResponse = useCase.verifyUsername(username)

        if(response.responseValue){
            view.showRegisterResponse("Try another username")
            view.showErrorNameField(response.msg)

        }else{
            view.drawSuccessUsernameField()
        }
    }

    internal fun verifyEmail(email: String){
        val response: UserResponse = useCase.verifyEmail(email)
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && !response.responseValue){
            view.drawSuccessEmailField()
        }else{
            view.showErrorEmailField("Wrong email format")
            view.showRegisterResponse("Wrong email format")
        }
    }

    internal fun verifyPass(pass: String, repeatPass: String){
         if (pass == repeatPass){
             view.drawSuccessPasswordField()
             view.drawSuccessRepeatPasswordField()
         }else{
             view.showRegisterResponse("Passwords don't coincide")
             view.showErrorPassField("Wrong password")
             view.showErrorRepeatPassField("Wrong password")
         }
    }

    internal fun registerNewUser(user: String, email:String, pass: String){
        val response: UserResponse = useCase.registerUser(user, email, pass)

        if(response.responseValue){
            view.showRegisterResponse("Success Register")
            view.navigateToHome()
        }else{
            view.showRegisterResponse("Error in register")
            view.showErrorNameField("Wrong username")
            view.showErrorPassField("Wrong password")
        }
    }

}