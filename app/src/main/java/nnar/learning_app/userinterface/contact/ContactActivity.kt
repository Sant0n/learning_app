package nnar.learning_app.userinterface.contact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.databinding.ActivityContactBinding
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.home.HomePresenter

class ContactActivity: AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set activity binding
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact info
        val contact = intent.getParcelableExtra<Contact>("contact")!!
        val name: String = contact.name
        val state: Boolean = contact.isOnline

        // Set contact info
        binding.contactName.text = name
        binding.stateInfo.text = if (state) "Online" else "Offline"
        binding.stateInfo.isEnabled = state
    }
}