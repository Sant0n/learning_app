package nnar.learning_app.userinterface.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // Set the Firebase Repository
    private val repository = ContactRepository(homeView.getCurrentUserUID())

    // The selected image when adding/editing a contact
    var selectedPicture: Uri? = null

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
        val binding = DialogEditContactBinding.bind(view)

        // Set dialog biding
        if (itemView != null && position != null) {
            binding.contactNameEdit.setText(itemView.getName())
            binding.stateSwitch.isChecked = repository.getContact(position).isOnline
        }

        // Set listener for picture upload
        binding.getPicture.setOnClickListener {
            checkPermissionForImage()
        }

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                viewModelScope.launch {
                    setPositiveButtonAction(binding, position, itemView)
                    homeView.updateAdapter()
                    dialogInterface.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setView(view)
            .show()
    }

    // Set action for Dialog Save
    private suspend fun setPositiveButtonAction(
        binding: DialogEditContactBinding,
        position: Int? = null,
        itemView: RowView? = null
    ) {
        // Set the new values
        val name = binding.contactNameEdit.text.toString()
        val state = binding.stateSwitch.isChecked

        // Set new picture
        var pic = selectedPicture?.let { repository.uploadPicture(it) }
        pic = pic ?: repository.getImageURI(position)

        // Set the new contact
        val contact = Contact(name, state, pic)

        // Set the modification
        itemView?.setContactView(contact)

        // Add/Modify contact
        repository.write(contact, position)

        // Reset selected picture
        selectedPicture = null
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
}