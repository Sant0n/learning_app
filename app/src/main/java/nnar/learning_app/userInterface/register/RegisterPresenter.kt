package nnar.learning_app.userInterface.register

import nnar.learning_app.dataInterface.RegisterView
import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.usecase.RegisterUserUsecase

class RegisterPresenter(
    private val view: RegisterView,
    private val registerUserUseCase: RegisterUserUsecase
) {

    internal fun signUp(email: String, username: String, password: String) {
        val response = registerUserUseCase.registerUser(User(email, username, password))

        if (!response.error) {
            /**
             * Go back to login screen showing msg successful
             */
        } else {
            /**
             * Refresh the screen showing the error
             */
        }
    }
}