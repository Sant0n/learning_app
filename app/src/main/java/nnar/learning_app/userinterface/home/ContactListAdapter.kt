package nnar.learning_app.userinterface.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R

/**
 * Manages the List Adapter and interacts with the [HomePresenter].
 * @constructor Receives the [presenter] to interact with the [HomeActivity].
 */
class ContactListAdapter(private val presenter: HomePresenter) :
    RecyclerView.Adapter<ContactViewHolder>() {

    /**
     * Usually involves inflating a layout from XML and returning the holder.
     * Gets the context from the [parent] and [viewType] to return the [ContactViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_contact, parent, false)

        // Return a new holder instance
        return ContactViewHolder(contactView)
    }

    /**
     * Involves populating data into the item through the [contactViewHolder] and [position].
     */
    override fun onBindViewHolder(contactViewHolder: ContactViewHolder, position: Int) {
        // Set contact row view
        presenter.setContact(contactViewHolder, position)

        // Set listeners
        setListeners(contactViewHolder, position)
    }

    /**
     * Returns the total count of items in the list
     */
    override fun getItemCount() = presenter.getNumberOfContacts()

    /**
     * Configures all the listeners for the given contact
     * through the [contactViewHolder] and [position].
     */
    private fun setListeners(contactViewHolder: ContactViewHolder, position: Int) {
        // Set listener for contact removal
        contactViewHolder.getRemoveButton().setOnClickListener {
            presenter.removeContact(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        // See contact details
        contactViewHolder.getContactPicture().setOnClickListener {
            presenter.seeContactDetails(contactViewHolder, position)
        }

        // Edit contact details
        contactViewHolder.getContactEdit().setOnClickListener {
            presenter.contactDialog(position)
        }
    }
}