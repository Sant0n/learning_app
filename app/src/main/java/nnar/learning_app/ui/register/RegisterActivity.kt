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
    private lateinit var email: TextView
    private lateinit var passwordText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameText = findViewById<TextView>(R.id.username_edittext_register)
        email = findViewById<TextView>(R.id.email_edittext_register)
        passwordText = findViewById<TextView>(R.id.password_edittext_register)

        val presenter = RegisterPresenter(this, RegisterUseCase(UserRepository()))
        val confirmButton = findViewById<Button>(R.id.register_confirm_button)

        confirmButton.setOnClickListener {
            presenter.registerNewUser(usernameText.text.toString(), email.text.toString(), passwordText.text.toString())
        }
    }

    override fun showRegisterResponse(s: String){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorNameField(s:String){
        usernameText.error = s
    }

    override fun showErrorPassField(s:String){
        passwordText.error = s
    }

    override fun navigateToHome(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

}