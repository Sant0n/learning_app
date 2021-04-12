package nnar.learning_app.userinterface.home;

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.DialogEditContactBinding
import nnar.learning_app.datainterface.RowView

class ContactListPresenter() {
    // Get number of contacts
    fun getNumberOfContacts(): Int {
        return ContactRepository.size()
    }

    // Remove contact
    fun removeContact(position: Int) {
        ContactRepository.removeContact(position)
    }

    // Get contact name
    fun getContactName(position: Int): String {
        return ContactRepository.getContactName(position)
    }

    // Set contact information
    fun setButtonState(view: RowView, position: Int, change: Boolean = true) {
        // Set button current state
        val state: Boolean = if(change) setButtonState(position) else getContactState(position)
        val stateText: String = getButtonStateText(position)
        view.setButtonState(stateText, state)
    }

    // See contact information
    fun seeContactDetails(position: Int, view: RowView) {
        view.seeDetails(ContactRepository.getContact(position))
    }

    // Show Alert Dialog to get new input
    fun showDialog(adapter: ContactsListAdapter, context: Context, position: Int, itemView: RowView) {
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
    private fun getButtonStateText(position: Int): String {
        return ContactRepository.getStateText(position)
    }

    // Change contact state
    private fun setButtonState(position: Int): Boolean {
        return ContactRepository.changeState(position)
    }

    // Get contact state
    private fun getContactState(position: Int): Boolean {
        return ContactRepository.getContactState(position)
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
    private fun modifyContact(position: Int,  name: String, state: Boolean) {
        // Activate dialog and get results
        ContactRepository.setName(position, name)
        ContactRepository.setState(position, state)
    }
}
