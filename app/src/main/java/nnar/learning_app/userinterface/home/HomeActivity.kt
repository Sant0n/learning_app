package nnar.learning_app.userinterface.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.userinterface.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create adapter passing in the presenter
        adapter = ContactsListAdapter()

        // Lookup the recyclerview in activity layout
        binding.recyclerView.adapter = adapter

        // Set layout manager to position the items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Set presenters
        presenter = HomePresenter()
        presenter.setContactList(adapter)

        // Set listeners
        setListeners()
    }

    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener {
            presenter.addContact()
            adapter.notifyDataSetChanged()
        }

        // Sign out
        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}