package nnar.learning_app.ui.register

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usercase.RegisterUserCase

class RegisterPresenter(private val view: RegisterView, private val userCase: RegisterUserCase) {

    internal fun verifyPass(pass: String, repeatPass: String): Boolean{
        return pass == repeatPass
    }

    internal fun registerNewUser(user: String, email:String, pass: String): Boolean{
        val response: UserResponse = userCase.registerUser(user, email, pass)
        return response.responseValue
    }

   /* internal fun emailFormat(email: String): Boolean{
        val regex = """/^\S+@\S+\.\S+$/""".toRegex()
        assertTrue(regex.matches(email))
    }*/
}