package nnar.learning_app.userinterface.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.domain.model.ContactViewHolder

class ContactsAdapter(var presenter: ContactListPresenter) : RecyclerView.Adapter<ContactViewHolder>() {

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_contact, parent, false)

        // Return a new holder instance
        return ContactViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(contactViewHolder: ContactViewHolder, position: Int) {
        // Get the data model based on position
        val contact: Contact = presenter.mContacts[position]

        // Set item views based on your views and data model
        contactViewHolder.setNameTextView(contact.name)

        // Set button text
        val stateText: String = presenter.getButtonState(contact.isOnline)
        contactViewHolder.setButtonState(stateText, contact.isOnline)

        // Set listeners
        setListeners(contactViewHolder, contact, position)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return presenter.getNumberOfContacts()
    }

    // Configure all the listeners
    private fun setListeners(contactViewHolder: ContactViewHolder, contact: Contact, position: Int) {
        // Set listener for status change
        contactViewHolder.getStateButton().setOnClickListener {
            contact.isOnline = !contact.isOnline
            val stateText: String = presenter.getButtonState(contact.isOnline)
            contactViewHolder.setButtonState(stateText, contact.isOnline)
        }

        // Set listener for contact removal
        contactViewHolder.getRemoveButton().setOnClickListener {
            presenter.removeContact(position)
            notifyItemRemoved(position)
        }

        // See contact details
        contactViewHolder.getSeeMore().setOnClickListener {
            presenter.seeContactDetails(contact)
        }
    }
}