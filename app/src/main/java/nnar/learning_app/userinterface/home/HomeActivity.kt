package nnar.learning_app.userinterface.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.userinterface.login.LoginActivity

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set presenters
        presenter = HomePresenter(this)

        // Create adapter passing in the presenter
        adapter = ContactsListAdapter(presenter)

        // Lookup the recyclerview in activity layout
        binding.recyclerView.adapter = adapter

        // Set layout manager to position the items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Update contacts
        presenter.setContactList()

        // Set listeners
        setListeners()
    }

    override fun updateAdapter() = adapter.notifyDataSetChanged()

    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener {
            presenter.addContact()
        }

        // Sign out
        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}