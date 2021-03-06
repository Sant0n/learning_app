package nnar.learning_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get reference to all views
        val etUsername = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val etPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.sign_id)

        // set on-click listener
        btnLogin.setOnClickListener {
            val username = etUsername.text;
            val password = etPassword.text.toString();

            // your code to validate the user_name and password combination
            if(password == "test") {
                Toast.makeText(this@MainActivity, username, Toast.LENGTH_LONG).show()
            } else {
                etPassword.error = "Wrong Password"
            }
        }
    }
}