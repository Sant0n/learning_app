package nnar.learning_app.userinterface.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.databinding.ActivityContactBinding
import nnar.learning_app.userinterface.home.HomePresenter

class ContactActivity: AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set activity binding
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact info
        val name: String = this.intent.getStringExtra("name")
        val state: Boolean = this.intent.getBooleanExtra("state", false)

        // Set contact info
        binding.contactName.text = name
        binding.stateInfo.text = if (state) "Online" else "Offline"
        binding.stateInfo.isEnabled = state
    }
}