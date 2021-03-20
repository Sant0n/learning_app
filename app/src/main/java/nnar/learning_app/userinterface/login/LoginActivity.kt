package nnar.learning_app.userinterface.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import nnar.learning_app.R
import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.databinding.ActivityLoginBinding
import nnar.learning_app.domain.usecase.LoginUserUsecase
import nnar.learning_app.userinterface.home.HomeActivity
import nnar.learning_app.userinterface.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, LoginUserUsecase(UserRepository()))
        setListeners()

    }

    override fun showErrorLogin() {
        binding.userId.setBackgroundResource(R.drawable.form_field_error)
        binding.userPassword.setBackgroundResource(R.drawable.form_field_error)

        val toast: Toast = Toast.makeText(this, R.string.login_error_msg, Toast.LENGTH_LONG)
        val view: View = toast.view
        view.setBackgroundResource(R.drawable.form_field_error)
        toast.show()
    }

    override fun loginSuccessful() {
        val toast: Toast = Toast.makeText(this, R.string.login_successful, Toast.LENGTH_LONG)
        val view: View = toast.view
        view.setBackgroundResource(R.drawable.successful_operation)
        toast.show()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun setListeners(){
        binding.gotoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            presenter.signIn(email = binding.userId.text.toString(),
                password = binding.userPassword.text.toString())
        }
    }

}