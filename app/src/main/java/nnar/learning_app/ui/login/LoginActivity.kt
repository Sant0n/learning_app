package nnar.learning_app.ui.login

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.R

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.databinding.ActivityLoginBinding
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.usercase.LoginUseCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity
import nnar.learning_app.utils.CommonFunctions

class LoginActivity :  AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var usernameText: TextView
    private lateinit var passwordText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usernameText = binding.usernameEdittextLogin
        passwordText = binding.passwordEdittextLogin
        confirmButton = binding.loginConfirmButton

        val presenter = LoginPresenter(this, LoginUseCase(UserRepository()))

        setListeners(presenter)
    }

    private fun setListeners(presenter: LoginPresenter) {
        usernameText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyUsername(usernameText.text.toString(), hasFocus)
        }

        passwordText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyPass(passwordText.text.toString(), hasFocus)
        }

        confirmButton.setOnClickListener {
            presenter.verifyUser(usernameText.text.toString(), passwordText.text.toString())
        }
    }

    override fun showLoginResponse(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    override fun showErrorNameField(s:String){
        usernameText.error = s
        usernameText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorPassField(s:String){
        passwordText.error = s
        passwordText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun drawSuccessUsernameField() {
        CommonFunctions().resetError(usernameText)
        usernameText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessPasswordField() {
        CommonFunctions().resetError(passwordText)
        passwordText.setBackgroundResource(R.drawable.textview_success_border)
    }
}