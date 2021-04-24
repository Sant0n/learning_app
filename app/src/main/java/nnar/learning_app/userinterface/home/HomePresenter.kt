package nnar.learning_app.userinterface.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.R
import nnar.learning_app.data.repository.FirebaseContactRepository
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact

class HomePresenter(private val homeView: HomeView) : ViewModel() {

    // Add new default contact
    fun addContact() = viewModelScope.launch {
        if (FirebaseContactRepository.addContact())
            homeView.updateAdapter()
    }

    // Get initial set of contacts
    fun setContactList() = viewModelScope.launch {
        if (FirebaseContactRepository.getCurrentContactsId())
            homeView.updateAdapter()
    }

    // Get number of contacts
    fun getNumberOfContacts() = FirebaseContactRepository.size()

    // Remove contact
    fun removeContact(position: Int) = FirebaseContactRepository.removeContact(position)

    // Set contact name
    fun setContactName(view: RowView, position: Int) = viewModelScope.launch {
        view.setNameTextView(FirebaseContactRepository.getContactName(position))
    }

    // Set contact information
    fun setButtonState(view: RowView, position: Int, change: Boolean = true) {
        viewModelScope.launch {
            val state: Boolean = if (change) setButtonState(position) else getContactState(position)
            val stateText: String = getButtonStateText(position)
            view.setButtonState(stateText, state)
        }
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

    // Get contact state text
    private suspend fun getButtonStateText(position: Int): String {
        return FirebaseContactRepository.getStateText(position)
    }

    // Change contact state
    private suspend fun setButtonState(position: Int): Boolean {
        return FirebaseContactRepository.changeState(position)
    }

    // Get contact state
    private suspend fun getContactState(position: Int): Boolean {
        return FirebaseContactRepository.getContactState(position)
    }

    // Set action for Dialog Save
    private fun setPositiveButtonAction(view: View, position: Int, itemView: RowView) {
        // Set dialog biding
        val dialogBinding = DialogEditContactBinding.bind(view)

        // Set the new values
        val name = dialogBinding.contactNameEdit.text.toString()
        val state = dialogBinding.stateSwitch.isChecked

        // Set the modification
        itemView.setNameTextView(name)
        itemView.setStateView(state)

        // Modify contact
        modifyContact(position, name, state)
    }

    // Modify contact information
    private fun modifyContact(position: Int, name: String, state: Boolean) {
        // Activate dialog and get results
        FirebaseContactRepository.write(Contact(name, state), position)
    }
}