package nnar.learning_app.ui.register

import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usercase.RegisterUseCase

class RegisterPresenter(private val view: RegisterView, private val useCase: RegisterUseCase) {

    internal fun verifyPass(pass: String, repeatPass: String): Boolean{
        return pass == repeatPass
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

    //internal fun verify
   /* internal fun emailFormat(email: String): Boolean{
        val regex = """/^\S+@\S+\.\S+$/""".toRegex()
        assertTrue(regex.matches(email))
    }*/
}