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

    override fun getName() = binding.contactName.text.toString()

    override fun getState() = binding.stateButton.isEnabled

    override fun setContactView(contact: Contact) {
        binding.contactName.text = contact.name
        setButtonState(contact)
    }

    override fun setButtonState(contact: Contact) {
        binding.stateButton.text = contact.getStateText()
        binding.stateButton.isEnabled = contact.isOnline
    }

    override fun seeMore(contact: Contact) {
        // Set intent for Contact Details
        val intent = Intent(listItemView.context, ContactInfoActivity::class.java)

        // Set contact info
        intent.putExtra("contact", contact)

        // Start activity
        listItemView.context.startActivity(intent)
    }
}