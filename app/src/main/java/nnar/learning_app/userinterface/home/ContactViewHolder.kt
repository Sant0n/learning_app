package nnar.learning_app.userinterface.home

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.databinding.ItemContactBinding
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.contact.ContactInfoActivity

class ContactViewHolder(private val listItemView: View) : RecyclerView.ViewHolder(listItemView), RowView {

    private var binding = ItemContactBinding.bind(listItemView)

    fun getStateButton() = binding.stateButton

    fun getRemoveButton() = binding.removeContact

    fun getSeeMore() = binding.seeMore

    fun getContactEdit() = binding.editContact

    override fun setNameTextView(text: String) {
        binding.contactName.text = text
    }

    override fun setButtonState(text: String, state: Boolean) {
        binding.stateButton.text = text
        binding.stateButton.isEnabled = state
    }

    override fun setStateView(state: Boolean) {
        binding.stateButton.isEnabled = state
    }

    override fun seeDetails(contact: Contact) {
        // Set intent for Contact Details
        val intent = Intent(listItemView.context, ContactInfoActivity::class.java)

        // Set contact info
        intent.putExtra("contact", contact)

        // Start activity
        listItemView.context.startActivity(intent)
    }
}