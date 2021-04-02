package nnar.learning_app.userinterface.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.view.*
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.userinterface.contact.ContactActivity

class HomeActivity: AppCompatActivity(), HomeView {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set presenter
        presenter = HomePresenter(this)

        // Create adapter passing in the presenter
        adapter = ContactsAdapter(presenter)

        // Lookup the recyclerview in activity layout
        var rvContacts = binding.recyclerView

        // Attach the adapter to the recyclerview to populate items
        rvContacts.adapter = adapter

        // Set layout manager to position the items
        rvContacts.layoutManager = LinearLayoutManager(this)

        // Set listeners
        setListeners()
    }

    override fun seeDetails(contact: ContactRepository) {
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("name", contact.name)
        intent.putExtra("state", contact.isOnline)
        startActivity(intent)
    }

    override fun contactRemoved(position: Int) {
        adapter.notifyItemRemoved(position)
    }

    private fun setListeners() {
        // Add new contact
        binding.addContact.add_contact.setOnClickListener() {
            presenter.addContact()
            adapter.notifyDataSetChanged()
        }
    }
}