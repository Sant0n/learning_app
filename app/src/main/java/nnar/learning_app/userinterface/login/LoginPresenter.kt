package nnar.learning_app.userinterface.login

import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.usecase.LoginUserUsecase

class LoginPresenter(private val view: LoginView, private val loginUserUseCase: LoginUserUsecase) {

    internal fun signIn(email: String, password: String) {
        val response = loginUserUseCase.loginUser(email, password)

        if (!response.error) {
            view.loginSuccessful()
        } else {
            view.showErrorLogin()
        }
    }
}