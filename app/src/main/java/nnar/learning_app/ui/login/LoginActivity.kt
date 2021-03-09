package nnar.learning_app.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.R

class LoginActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val confirmButton = findViewById<Button>(R.id.login_confirm_button)

        confirmButton.setOnClickListener {
            val usernameText = findViewById<TextView>(R.id.username_edittext_login).editableText.toString()
            val passwordText = findViewById<TextView>(R.id.password_edittext_login).editableText.toString()

            //println(usernameText)
            if(usernameText.equals("nicole") && passwordText.equals("nicole")){
                Toast.makeText(this, "Sucess Login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}