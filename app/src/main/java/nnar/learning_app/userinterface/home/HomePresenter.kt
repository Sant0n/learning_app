package nnar.learning_app.userinterface.home

import android.app.AlertDialog
import android.view.LayoutInflater
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
        view.setContactView(contact)
    }

    // Set contact information
    fun changeState(view: RowView, position: Int) {
        val contact = repository.changeState(position)
        view.setButtonState(contact)
    }

    // See contact information
    fun seeContactDetails(view: RowView) = view.seeMore(Contact(view.getName(), view.getState()))

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
        if (itemView != null) {
            binding.contactNameEdit.setText(itemView.getName())
            binding.stateSwitch.isChecked = itemView.getState()
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
        itemView?.setContactView(contact)

        // Add/Modify contact
        repository.write(contact, position)
    }
}