package nnar.learning_app.userInterface.register

import nnar.learning_app.dataInterface.RegisterView
import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.usecase.RegisterUserUsecase
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterPresenter(
    private val view: RegisterView,
    private val registerUserUseCase: RegisterUserUsecase
) {

    internal fun signUp(email: String, username: String, password: String, rep_password: String) {
        //val response = registerUserUseCase.registerUser(User(email, username, password))

        //if (!response.error) {
        /**
         * Go back to login screen showing msg successful
         */
        //} else {
        /**
         * Refresh the screen showing the error
         */
        //}
    }

    internal fun checkPasswords(p1: String, p2: String, focus: Boolean) {
        if (!focus) {
            if (p1 == p2) {
                view.showSuccessfulPassBox()
            } else {
                view.showErrorPassBox()
            }
        }
    }

    internal fun checkEmailValid(email: String, focus: Boolean) {
        if (!focus) {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher: Matcher = pattern.matcher(email)
            if (matcher.matches()) {
                view.showSuccessfulEmailBox()
            } else {
                view.showErrorEmailBox()
            }
        }
    }
}