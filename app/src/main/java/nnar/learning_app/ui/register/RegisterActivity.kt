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
import nnar.learning_app.domain.usercase.RegisterUserCase
import nnar.learning_app.ui.mainmenu.MainMenuActivity


class RegisterActivity: AppCompatActivity(), RegisterView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val presenter = RegisterPresenter(this, RegisterUserCase( UserRepository() ) )
        val confirmButton = findViewById<Button>(R.id.register_confirm_button)

        confirmButton.setOnClickListener {
            val usernameText = findViewById<TextView>(R.id.username_edittext_register)
            val email = findViewById<TextView>(R.id.email_edittext_register)
            val passwordText = findViewById<TextView>(R.id.password_edittext_register)

            val responseRegister = presenter.registerNewUser(usernameText.text.toString(), email.text.toString(), passwordText.text.toString())

            if(responseRegister){
                Toast.makeText(this, "Success Register", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error in register", Toast.LENGTH_SHORT).show()
                usernameText.error = "Wrong username"
                passwordText.error = "Wrong password"
            }
        }
    }

}