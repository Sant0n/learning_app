package nnar.learning_app.userinterface.home

import android.app.AlertDialog
import android.net.Uri
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
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
    // Set the Firebase Repository
    private val repository = ContactRepository(homeView.getCurrentUserUID())

    // Get initial set of contacts
    fun setContactList() = viewModelScope.launch {
        if (repository.getCurrentContactsId())
            homeView.updateAdapter()
    }

    // Get number of contacts
    fun getNumberOfContacts() = repository.size()

    // Remove contact
    fun removeContact(position: Int) = repository.removeContact(position)

    // Set contact details
    fun setContact(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        view.setContactView(contact, setImage(view))
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
        if (itemView != null && position != null)  {
            binding.contactNameEdit.setText(itemView.getName())
            binding.stateSwitch.isChecked = repository.getContact(position).isOnline
        }

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                setPositiveButtonAction(binding, position, itemView)
                homeView.updateAdapter()
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .setView(view)
            .show()
    }

    // Set action for Dialog Save
    private fun setPositiveButtonAction(
        binding: DialogEditContactBinding,
        position: Int? = null,
        itemView: RowView? = null
    ) {
        // Set the new values
        val name = binding.contactNameEdit.text.toString()
        val state = binding.stateSwitch.isChecked
        val contact = Contact(name, state)

        // Set the modification
        itemView?.setContactView(contact, setImage(itemView))

        // Add/Modify contact
        repository.write(contact, position)
    }

    // Get random picture for contact
    private fun setImage(view: RowView): Uri {
        // Get the attributes
        val uri = repository.getImageURI()
        val image = view.getContactPicture()

        // Load image into resource
        Picasso.get().load(uri).resize(1000, 1000).centerCrop().into(image)

        // Return current image
        return uri
    }
}