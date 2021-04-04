package nnar.learning_app.userinterface.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.ContactViewHolder

class ContactsListAdapter(var presenter: ContactListPresenter) : RecyclerView.Adapter<ContactViewHolder>() {

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
        // Set item views based on your views and data model
        contactViewHolder.setNameTextView(presenter.getContactName(position))

        // Set button text
        presenter.setButtonState(contactViewHolder, position, false)

        // Set listeners
        setListeners(contactViewHolder, position)
    }

    // Returns the total count of items in the list
    override fun getItemCount() = presenter.getNumberOfContacts()

    // Configure all the listeners
    private fun setListeners(contactViewHolder: ContactViewHolder, position: Int) {
        // Set listener for status change
        contactViewHolder.getStateButton().setOnClickListener {
            presenter.setButtonState(contactViewHolder, position)
        }

        // Set listener for contact removal
        contactViewHolder.getRemoveButton().setOnClickListener {
            presenter.removeContact(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        // See contact details
        contactViewHolder.getSeeMore().setOnClickListener {
            presenter.seeContactDetails(position)
        }
    }
}