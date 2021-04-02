package nnar.learning_app.userinterface.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.data.repository.ContactViewHolder

class ContactsAdapter (private var presenter: HomePresenter) : RecyclerView.Adapter<ContactViewHolder>() {

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
        // Call presenter to set the data
        presenter.onBindRepositoryRowViewAtPosition(contactViewHolder, position)
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return presenter.getNumberOfContacts()
    }
}