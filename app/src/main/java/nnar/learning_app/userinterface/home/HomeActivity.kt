package nnar.learning_app.userinterface.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import nnar.learning_app.R
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.login.LoginActivity
import nnar.learning_app.util.Notification

/**
 * The main view for the app.
 * @constructor Creates an empty constructor
 */
class HomeActivity : AppCompatActivity(), HomeView {
    // Activities main variables
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dialogBinding: DialogEditContactBinding
    private lateinit var presenter: HomePresenter
    private lateinit var adapter: ContactListAdapter

    // Gallery permissions
    private val requestGalleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            presenter.checkPermissions(it)
        }
    private val openGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        presenter.selectedPicture(it)
    }

    /**
     * Creates the activity, sets the presenter and the listeners.
     * [savedInstanceState] contains the current state of the app
     */
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

    /**
     * Destroy the activity
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.reset()
    }

    /**
     * Notify dataset change to the adapter
     */
    override fun updateAdapter() = adapter.notifyDataSetChanged()

    /**
     * Returns the UID form current user
     */
    override fun getCurrentUserUID() = Firebase.auth.currentUser!!.uid

    /**
     * Returns the Home Activity context
     */
    override fun getContext(): Context = binding.root.context

    /**
     * Opens the gallery to look for the picture to upload
     */
    override fun selectPicture() = openGallery.launch("image/*")

    /**
     * Sets the new Picasso image by receiving the [uri] for the new picture
     */
    override fun setNewContactPicture(uri: Uri) = setPicasso(uri, dialogBinding.editContactPicture)

    /**
     * Set Dialog content where [contact] contains the information to set the dialog
     */
    override fun setDialogContent(contact: Contact) {
        dialogBinding.contactNameEdit.setText(contact.name)
        dialogBinding.stateSwitch.isChecked = contact.isOnline
        setNewContactPicture(Uri.parse(contact.pic))
    }

    /**
     * Show Alert Dialog to get new input. [position] indicates the contact to modify
     * or null if it is a new contact.
     */
    override fun contactDialog(position: Int?) {
        // Inflate the dialog alert view
        val context = getContext()
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_contact, null)

        // Get the dialog binding
        dialogBinding = DialogEditContactBinding.bind(view)

        // Set dialog biding
        presenter.setCurrentContactInfo(position)

        // Set listener for picture upload
        dialogBinding.getPicture.setOnClickListener {
            requestGalleryPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                val name = dialogBinding.contactNameEdit.text.toString()
                val state = dialogBinding.stateSwitch.isChecked
                presenter.setPositiveButtonAction(name, state, position)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setView(view)
            .show()
    }

    /**
     * Generate new added user notification
     */
    override fun generateNotification() {
        // Set current channel ID
        val notificationID = Notification().createNotificationChannel(getContext())

        // Set notification content
        val builder = NotificationCompat.Builder(this, notificationID)
            .setSmallIcon(R.drawable.ic_baseline_account_circle_24)
            .setContentTitle("Successful")
            .setContentText("A new user has been added!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Build notification
        with(NotificationManagerCompat.from(this)) {
            notify(123, builder.build())
        }
    }

    /**
     * Configure all the listeners
     */
    private fun setListeners() {
        // Add new contact
        binding.addContact.setOnClickListener {
            contactDialog(null)
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

    /**
     * Set profile picture
     */
    private fun setProfilePicture() {
        // Get the attributes
        val uri = Firebase.auth.currentUser!!.photoUrl!!
        val image = binding.profilePic

        // Load image into resource
        setPicasso(uri, image, 500, 500)
    }

    /**
     * Set picasso object by receiving the image [uri] and the [image] object.
     * Could also set the [width] and [height] for the image. The default is 150 for each one.
     */
    private fun setPicasso(uri: Uri, image: ImageView, width: Int = 150, height: Int = 150) {
        Picasso.get().load(uri).placeholder(R.drawable.placeholder_contact)
            .resize(width, height).centerCrop().into(image)
    }
}