package nnar.learning_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.R
import nnar.learning_app.domain.model.User
import nnar.learning_app.ui.mainmenu.MainMenuActivity

class LoginActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val confirmButton = findViewById<Button>(R.id.login_confirm_button)

        confirmButton.setOnClickListener {
            val usernameText = findViewById<TextView>(R.id.username_edittext_login)
            val passwordText = findViewById<TextView>(R.id.password_edittext_login)

            //println(usernameText)
            usernameText.text.toString()
            val user = User(username = usernameText.editableText.toString(), email = "",password = passwordText.editableText.toString())

            if((usernameText.text.toString()) == "nicole" && (passwordText.text.toString()) == "nicole"){
                Toast.makeText(this, "Sucess Login", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            }else{
                usernameText.error = "Wrong username"
                passwordText.error = "Wrong password"
            }
        }
    }


}