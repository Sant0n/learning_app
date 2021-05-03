package nnar.learning_app.userinterface.home

import android.content.Context
import android.content.Intent
import android.net.Uri
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
    // Activities main variables
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactListAdapter

    // Create the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding root
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set presenters
        presenter = HomePresenter(this)

        // Create adapter passing in the presenter
        adapter = ContactListAdapter(presenter)

        // Lookup the recyclerview in activity layout
        binding.recyclerView.adapter = adapter

        // Set layout manager to position the items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Set profile picture
        presenter.setProfilePicture()

        // Update contacts
        presenter.setContactList()

        // Set listeners
        setListeners()
    }

    // Destroy the activity
    override fun onDestroy() {
        super.onDestroy()
        presenter.reset()
    }

    // Notify dataset change to the adapter
    override fun updateAdapter() = runOnUiThread { adapter.notifyDataSetChanged() }

    // Get UID form current user
    override fun getCurrentUserUID() = Firebase.auth.currentUser!!.uid

    // Get the Home Activity context
    override fun getContext(): Context = binding.root.context

    // Get the contact picture
    override fun getContactPic() = binding.profilePic

    // Get current user's picture
    override fun getGetUserPicture(): Uri = Firebase.auth.currentUser!!.photoUrl

    // Configure all the listeners
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