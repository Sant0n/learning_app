package nnar.learning_app.ui.login

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.R

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.usercase.LoginUseCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity

class LoginActivity :  AppCompatActivity(), LoginView {

    private lateinit var usernameText: TextView
    private lateinit var passwordText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameText = findViewById<TextView>(R.id.username_edittext_login)
        passwordText = findViewById<TextView>(R.id.password_edittext_login)


        val confirmButton = findViewById<Button>(R.id.login_confirm_button)
        val presenter = LoginPresenter(this, LoginUseCase(UserRepository()))


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
    }

    override fun showErrorPassField(s:String){
        passwordText.error = s
    }

}