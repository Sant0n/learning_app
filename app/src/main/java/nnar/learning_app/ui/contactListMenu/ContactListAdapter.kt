package nnar.learning_app.ui.contactListMenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Contact

class ContactListAdapter(private val contactList: MutableSet<Contact>):
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(contactList.elementAt(position))

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, )
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

}