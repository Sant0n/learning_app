package nnar.learning_app.ui.mainmenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import nnar.learning_app.R
import nnar.learning_app.datainterface.MainMenuView
import nnar.learning_app.ui.login.LoginActivity
import nnar.learning_app.ui.register.RegisterActivity

class MainMenuActivity : AppCompatActivity(), MainMenuView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val buttonLogin = findViewById<Button>(R.id.login_button)
        val buttonRegister = findViewById<Button>(R.id.register_button)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}