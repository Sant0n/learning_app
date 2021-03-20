package nnar.learning_app.userinterface.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {

    lateinit var contacts: ArrayList<ContactRepository>
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lookup the recyclerview in activity layout
        val rvContacts = binding.recyclerView

        // Initialize contacts
        contacts = ContactRepository.createContactsList(20)

        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(contacts)

        // Attach the adapter to the recyclerview to populate items
        rvContacts.adapter = adapter

        // Set layout manager to position the items
        rvContacts.layoutManager = LinearLayoutManager(this)
    }

}