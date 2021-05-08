package nnar.learning_app.userinterface.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.databinding.ItemContactBinding
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.contact.ContactInfoActivity


class ContactViewHolder(private val view: View) : RecyclerView.ViewHolder(view), RowView {
    // Binding for the rows
    private var binding = ItemContactBinding.bind(view)

    // Get button state
    fun getStateButton() = binding.stateButton

    // Get remove button
    fun getRemoveButton() = binding.removeContact

    // Get see more button
    fun getSeeMore() = binding.seeMore

    // Get contact edit button
    fun getContactEdit() = binding.editContact

    // Get the context
    override fun getContext(): Context = view.context

    // Get contact name
    override fun getName() = binding.contactName.text.toString()

    // Get state name
    override fun getState() = binding.stateButton.isEnabled

    // Set contact info
    override fun setContactView(contact: Contact) {
        binding.contactName.text = contact.name
        setButtonState(contact)
    }

    // Set button state info
    override fun setButtonState(contact: Contact) {
        binding.stateButton.text = contact.getStateText()
        binding.stateButton.isEnabled = contact.isOnline
    }

    // Go to Contact Info Activity
    override fun seeMore(contact: Contact, uid: String) {
        // Set intent for Contact Details
        val intent = Intent(view.context, ContactInfoActivity::class.java)

        // Set contact info
        intent.putExtra("contact", contact)
        intent.putExtra("uid", uid)

        // Start activity
        val options = ActivityOptions.makeSceneTransitionAnimation(
            view.context as Activity,
            binding.contactName,
            "contact_name"
        )
        view.context.startActivity(intent, options.toBundle())
    }
}