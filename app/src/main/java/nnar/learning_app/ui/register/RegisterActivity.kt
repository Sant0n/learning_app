package nnar.learning_app.ui.register

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.R

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.databinding.ActivityRegisterBinding
import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.usercase.RegisterUseCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity
import nnar.learning_app.utils.CommonFunctions


class RegisterActivity: AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var usernameText: TextView
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView
    private lateinit var repeatPasswordText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usernameText = binding.usernameEdittextRegister
        emailText = binding.emailEdittextRegister
        passwordText = binding.passwordEdittextRegister
        repeatPasswordText= binding.passwordEdittextRegisterRepeat
        confirmButton = binding.registerConfirmButton

        val presenter = RegisterPresenter(this, RegisterUseCase(UserRepository()))

        setListeners(presenter)
    }

    private fun setListeners(presenter: RegisterPresenter) {
        usernameText.setOnFocusChangeListener { _, hasFocus ->
           presenter.verifyUsername(usernameText.text.toString(), hasFocus, usernameText.text.isNotBlank() )

        }
        emailText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyEmail(emailText.text.toString(), hasFocus, emailText.text.isNotBlank())
        }
        repeatPasswordText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyPass(passwordText.text.toString(), repeatPasswordText.text.toString(),
                hasFocus, passwordText.text.isNotBlank(), repeatPasswordText.text.isNotBlank())

        }
        confirmButton.setOnClickListener {
            presenter.registerNewUser(usernameText.text.toString(), emailText.text.toString(),
                passwordText.text.toString(), repeatPasswordText.text.toString())
        }
    }

    override fun showRegisterResponse(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorNameField(s:String){
        usernameText.error = s
        usernameText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorEmailField(s: String) {
        emailText.error = s
        emailText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorPassField(s:String){
        passwordText.error = s
        passwordText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorRepeatPassField(s: String) {
        repeatPasswordText.error = s
        repeatPasswordText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun drawSuccessUsernameField() {
        CommonFunctions().resetError(usernameText)
        usernameText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessEmailField() {
        CommonFunctions().resetError(emailText)
        emailText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessPasswordField() {
        CommonFunctions().resetError(passwordText)
        passwordText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessRepeatPasswordField() {
        CommonFunctions().resetError(repeatPasswordText)
        repeatPasswordText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun navigateToHome(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}