package nnar.learning_app.userInterface.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nnar.learning_app.R
import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.dataInterface.LoginView
import nnar.learning_app.domain.usecase.LoginUserUsecase

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this, LoginUserUsecase(UserRepository()))
    }
}