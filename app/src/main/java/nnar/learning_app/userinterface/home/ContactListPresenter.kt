package nnar.learning_app.userinterface.home;

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import nnar.learning_app.R
import nnar.learning_app.data.repository.FirebaseContactRepository
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.domain.model.ContactViewHolder

class ContactListPresenter() : ViewModel() {

    // Get number of contacts
    fun getNumberOfContacts(): Int {
        return FirebaseContactRepository.size()
    }

    // Remove contact
    fun removeContact(adapter: RecyclerView.Adapter<ContactViewHolder>, position: Int) {
        viewModelScope.launch {
            if (FirebaseContactRepository.removeContact(position)) {
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, getNumberOfContacts())
            }
        }
    }

    // Get contact name
    fun getContactName(view: RowView, position: Int) {
        viewModelScope.launch {
            view.setNameTextView(FirebaseContactRepository.getContactName(position))
        }
    }

    // Set contact information
    fun setButtonState(view: RowView, position: Int, change: Boolean = true) {
        // Set button current state
        viewModelScope.launch {
            val state: Boolean = if (change) setButtonState(position) else getContactState(position)
            val stateText: String = getButtonStateText(position)
            view.setButtonState(stateText, state)
        }
    }

    // See contact information
    fun seeContactDetails(position: Int, view: RowView) {
        viewModelScope.launch {
            view.seeDetails(FirebaseContactRepository.getContact(position))
        }
    }

    // Show Alert Dialog to get new input
    fun showDialog(
        adapter: RecyclerView.Adapter<ContactViewHolder>,
        context: Context,
        position: Int,
        itemView: RowView
    ) {
        // Inflate the dialog alert view
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_contact, null)

        // Build the Alert Dialog
        AlertDialog.Builder(context)
            .setTitle("Edit Contact Information")
            .setPositiveButton("Save") { dialogInterface, _ ->
                // Set dialog binding
                setPositiveButtonAction(view, position, itemView)
                adapter.notifyDataSetChanged()
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
