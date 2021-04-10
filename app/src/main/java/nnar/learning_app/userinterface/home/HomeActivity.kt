package nnar.learning_app.userinterface.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact repository
        ContactRepository.getContacts()

        // Set presenters
        presenter = HomePresenter()

        // Create adapter passing in the presenter
        adapter = ContactsListAdapter()

        // Lookup the recyclerview in activity layout
        binding.recyclerView.adapter = adapter

        // Set layout manager to position the items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Set listeners
        setListeners()
    }

    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener() {
            presenter.addContact()
            adapter.notifyDataSetChanged()
        }
    }
}