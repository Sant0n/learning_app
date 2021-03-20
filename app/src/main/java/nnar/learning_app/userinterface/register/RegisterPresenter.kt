package nnar.learning_app.userinterface.register

import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.usecase.RegisterUserUsecase
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterPresenter(
    private val view: RegisterView,
    private val registerUserUseCase: RegisterUserUsecase

) {
    private var samePass = false

    internal fun signUp(email: String, username: String, password: String, rep_password: String) {
        checkPasswords(password, rep_password)
        if (samePass) {
            val response = registerUserUseCase.registerUser(User(email, username, password))

            if (!response.error) {
                view.registerSuccessful()
            } else {
                view.registerError()
            }
        }
    }

    internal fun checkPasswords(p1: String, p2: String, focus: Boolean = false) {
        if (!focus) {
            samePass = if (p1 == p2) {
                view.showSuccessfulPassBox()
                true
            } else {
                view.showErrorPassBox()
                false
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