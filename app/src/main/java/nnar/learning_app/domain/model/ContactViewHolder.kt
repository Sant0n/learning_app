package nnar.learning_app.domain.model

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.databinding.ItemContactBinding
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.userinterface.contact.ContactInfoActivity

class ContactViewHolder(private val listItemView: View) : RecyclerView.ViewHolder(listItemView), RowView {

    private var binding = ItemContactBinding.bind(listItemView)

    fun setNameTextView(text: String) {
        binding.contactName.text = text
    }

    fun getStateButton(): Button {
        return binding.stateButton
    }

    fun getRemoveButton(): Button {
        return binding.removeContact
    }

    fun getSeeMore(): ImageButton {
        return binding.seeMore
    }

    override fun setButtonState(text: String, state: Boolean) {
        binding.stateButton.text = text
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