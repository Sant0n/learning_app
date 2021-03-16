package nnar.learning_app.ui.register

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.R

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.usercase.RegisterUseCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity


class RegisterActivity: AppCompatActivity(), RegisterView {


    private lateinit var usernameText: TextView
    private lateinit var emailText: TextView
    private lateinit var passwordText: TextView
    private lateinit var repeatPasswordText: TextView
    private lateinit var confirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameText = findViewById(R.id.username_edittext_register)
        emailText = findViewById(R.id.email_edittext_register)
        passwordText = findViewById(R.id.password_edittext_register)
        repeatPasswordText= findViewById(R.id.repeatpass_register_textView)
        confirmButton = findViewById(R.id.register_confirm_button)

        val presenter = RegisterPresenter(this, RegisterUseCase(UserRepository()))

        setListeners(presenter)
    }

    private fun setListeners(presenter: RegisterPresenter) {
        usernameText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && usernameText.text.isNotBlank()){
                presenter.verifyUsername(usernameText.text.toString())
            }
        }
        emailText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && emailText.text.isNotBlank()) {
                presenter.verifyEmail(emailText.text.toString())
            }
        }
        repeatPasswordText.setOnFocusChangeListener { _, hasFocus ->
            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    "+hasFocus)
            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    "+ repeatPasswordText.text.isNotBlank())
            println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    "+ passwordText.text.isNotBlank())
            if(!hasFocus &&
                (repeatPasswordText.text.isNotBlank() &&
                        passwordText.text.isNotBlank())){
                presenter.verifyPass(passwordText.text.toString(), repeatPasswordText.text.toString())
            }
        }
        confirmButton.setOnClickListener {
            presenter.registerNewUser(usernameText.text.toString(), emailText.text.toString(), passwordText.text.toString())
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
        usernameText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessEmailField() {
        emailText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessPasswordField() {
        passwordText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessRepeatPasswordField() {
        repeatPasswordText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun navigateToHome(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}