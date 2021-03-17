package nnar.learning_app.ui.mainmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import nnar.learning_app.databinding.ActivityMainMenuBinding
import nnar.learning_app.ui.login.LoginActivity
import nnar.learning_app.ui.register.RegisterActivity

class MainMenuActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var logInButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        logInButton = binding.loginButton
        registerButton = binding.registerButton

        setListeners()
    }

    private fun setListeners() {

        logInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}