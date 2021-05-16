package nnar.learning_app.userinterface.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import nnar.learning_app.R
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact

@ExperimentalCoroutinesApi
class HomePresenter(private val homeView: HomeView) : ViewModel() {
    // Codes
    private val PERMISSION_CODE_READ: Int = 2
    private val PERMISSION_CODE_WRITE: Int = 3

    // Set the attribute
    companion object

    var selectedPicture: Uri? = null
    private lateinit var binding: DialogEditContactBinding
    private val repository = ContactRepository(homeView.getCurrentUserUID())

    // Get initial set of contacts
    fun setContactList() = viewModelScope.launch {
        if (repository.loadImages() && repository.getCurrentContactsId())
            homeView.updateAdapter()
    }

    // Get number of contacts
    fun getNumberOfContacts() = repository.size()

    // Remove contact
    fun removeContact(position: Int) = repository.removeContact(position)

    // Set contact details
    fun setContact(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        view.setContactView(contact)
    }

    // See contact information
    fun seeContactDetails(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        val uid = homeView.getCurrentUserUID()
        view.seeMore(contact, uid)
    }

    // Reset contacts list
    fun reset() = repository.reset()

    // Show Alert Dialog to get new input
    fun contactDialog(position: Int? = null, itemView: RowView? = null) {
        // Inflate the dialog alert view
        val context = itemView?.getContext() ?: homeView.getContext()
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_contact, null)

        // Get the dialog binding
        binding = DialogEditContactBinding.bind(view)

        // Set dialog biding
        if (itemView != null && position != null) {
            val contact = repository.getContact(position)
            binding.contactNameEdit.setText(contact.name)
            binding.stateSwitch.isChecked = contact.isOnline
            setPicassoPicture(contact.pic, binding.editContactPicture)
        }

        // Set listener for picture upload
        binding.getPicture.setOnClickListener {
            checkPermissionForImage()
        }

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                setPositiveButtonAction(position)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setView(view)
            .show()
    }

    // Se the selected picture on the Dialog
    fun setEditContactPicture(requestCode: Int, resultCode: Int, data: Intent?, code: Int) {
        if (resultCode == Activity.RESULT_OK && requestCode == code) {
            selectedPicture = data?.data!!
            setPicassoPicture(selectedPicture.toString(), binding.editContactPicture)
        }
    }

    // Set action for Dialog Save
    private fun setPositiveButtonAction(position: Int? = null) {
        // Set the new values
        val name = binding.contactNameEdit.text.toString()
        val state = binding.stateSwitch.isChecked
        val pic = selectedPicture?.toString() ?: repository.getImageURI(position)

        // Set the new contact
        val contact = Contact(name, state, pic)

        // Add/Modify contact if needed
        if (selectedPicture == null) {
            repository.write(contact, position)
            homeView.updateAdapter()
        } else {
            viewModelScope.launch {
                if (repository.uploadPicture(selectedPicture!!, contact, position)) {
                    selectedPicture = null
                    homeView.updateAdapter()
                }
            }
        }
    }

    // Check image permissions
    private fun checkPermissionForImage() {
        // Get the permission codes
        val readPermissions = Manifest.permission.READ_EXTERNAL_STORAGE
        val writePermissions = Manifest.permission.WRITE_EXTERNAL_STORAGE

        // Check permissions
        val context = homeView.getContext()
        if ((checkSelfPermission(context, readPermissions) == PackageManager.PERMISSION_DENIED)
            && (checkSelfPermission(context, writePermissions) == PackageManager.PERMISSION_DENIED)
        ) {
            // Get the require permissions
            val permission = arrayOf(readPermissions)
            val permissionCoarse = arrayOf(writePermissions)

            // Request permissions
            requestPermissions(context as Activity, permission, PERMISSION_CODE_READ)
            requestPermissions(context, permissionCoarse, PERMISSION_CODE_WRITE)
        } else {
            homeView.selectPicture()
        }
    }

    // Set the picture on the dialog
    private fun setPicassoPicture(pic: String, image: ImageView) {
        Picasso.get()
            .load(Uri.parse(pic))
            .resize(150, 150)
            .centerCrop()
            .into(image)
    }
}