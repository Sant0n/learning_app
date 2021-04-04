package nnar.learning_app.userinterface.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.contact.ContactInfoActivity

class HomeActivity : AppCompatActivity(), HomeView {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homePresenter: HomePresenter
    private lateinit var listPresenter: ContactListPresenter
    private lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact repository
        ContactRepository.getContacts()

        // Set presenters
        homePresenter = HomePresenter()
        listPresenter = ContactListPresenter(this)

        // Create adapter passing in the presenter
        adapter = ContactsListAdapter(listPresenter)

        // Lookup the recyclerview in activity layout
        var rvContacts = binding.recyclerView

        // Attach the adapter to the recyclerview to populate items
        rvContacts.adapter = adapter

        // Set layout manager to position the items
        rvContacts.layoutManager = LinearLayoutManager(this)

        // Set listeners
        setListeners()
    }

    override fun seeDetails(contact: Contact) {
        // Set intent for Contact Details
        val intent = Intent(this, ContactInfoActivity::class.java)

        // Set contact info
        intent.putExtra("contact", contact)

        // Start activity
        startActivity(intent)
    }

    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener() {
            homePresenter.addContact()
            adapter.notifyDataSetChanged()
        }
    }
}