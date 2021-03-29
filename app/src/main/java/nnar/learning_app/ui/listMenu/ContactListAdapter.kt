package nnar.learning_app.ui.listMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.domain.model.Contact

class ContactListAdapter(private val contactList: MutableSet<Contact>):
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList.elementAt(position))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

}