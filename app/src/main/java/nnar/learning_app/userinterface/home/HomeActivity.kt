package nnar.learning_app.userinterface.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.userinterface.login.LoginActivity

@ExperimentalCoroutinesApi
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.reset()
    }

    override fun updateAdapter() = adapter.notifyDataSetChanged()

    override fun getCurrentUserUID() = Firebase.auth.currentUser!!.uid

    override fun getContext(): Context = binding.root.context

    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener {
            presenter.contactDialog()
        }

        // Sign out
        binding.signOut.setOnClickListener {
            // Logout from Google
            Firebase.auth.signOut()
            finish()

            // Go back to login
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("logout", true)
            startActivity(intent)
        }
    }
}