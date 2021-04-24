package nnar.learning_app.userinterface.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import nnar.learning_app.R
import nnar.learning_app.data.repository.FirebaseRepository
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact

@ExperimentalCoroutinesApi
class HomePresenter(private val homeView: HomeView) : ViewModel() {
    // Set the Firebase Repository
    private val repository = FirebaseRepository(homeView.getCurrentUserUID())

    // Add new default contact
    fun addContact() = viewModelScope.launch {
        if (repository.addContact())
            homeView.updateAdapter()
    }

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
    fun setContact(view: RowView, position: Int) = viewModelScope.launch {
        val contact = repository.getContact(position)
        view.setContactView(contact)
    }

    // Set contact information
    fun changeState(view: RowView, position: Int) = viewModelScope.launch {
        val contact = repository.changeState(position)
        view.setButtonState(contact)
    }

    // See contact information
    fun seeContactDetails(view: RowView) = view.seeMore(Contact(view.getName(), view.getState()))

    // Show Alert Dialog to get new input
    fun showDialog(context: Context, position: Int, itemView: RowView) {
        // Inflate the dialog alert view
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_contact, null)
        val binding = DialogEditContactBinding.bind(view)

        // Set dialog biding
        binding.contactNameEdit.setText(itemView.getName())
        binding.stateSwitch.isChecked = itemView.getState()

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Edit Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                setPositiveButtonAction(view, position, itemView)
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
    private fun setPositiveButtonAction(view: View, position: Int, itemView: RowView) {
        // Set dialog biding
        val dialogBinding = DialogEditContactBinding.bind(view)

        // Set the new values
        val name = dialogBinding.contactNameEdit.text.toString()
        val state = dialogBinding.stateSwitch.isChecked
        val contact = Contact(name, state)

        // Set the modification
        itemView.setContactView(contact)

        // Modify contact
        modifyContact(contact, position)
    }

    // Modify contact information
    private fun modifyContact(contact: Contact, position: Int) = repository.write(contact, position)
}