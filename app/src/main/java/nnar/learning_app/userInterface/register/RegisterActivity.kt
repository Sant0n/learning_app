package nnar.learning_app.userInterface.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nnar.learning_app.R
import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.dataInterface.RegisterView
import nnar.learning_app.domain.usecase.LoginUserUsecase
import nnar.learning_app.domain.usecase.RegisterUserUsecase
import nnar.learning_app.userInterface.login.LoginPresenter

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this, RegisterUserUsecase(UserRepository()))
    }
}