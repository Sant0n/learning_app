package nnar.learning_app.userinterface.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
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
        setProfilePicture()

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
    override fun updateAdapter() = adapter.notifyDataSetChanged()

    // Get UID form current user
    override fun getCurrentUserUID() = Firebase.auth.currentUser!!.uid

    // Get the Home Activity context
    override fun getContext(): Context = binding.root.context

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

    // Set profile picture
    private fun setProfilePicture() {
        // Get the attributes
        val uri = Firebase.auth.currentUser!!.photoUrl!!
        val image = binding.profilePic

        // Load image into resource
        setPicasso(uri, image, 500, 500)
    }

    // Configure Picasso image
    private fun setPicasso(uri: Uri, image: ImageView, width: Int, height: Int) {
        Picasso.get().load(uri).resize(width, height).centerCrop().into(image)
    }
}