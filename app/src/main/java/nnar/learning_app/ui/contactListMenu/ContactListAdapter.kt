package nnar.learning_app.ui.contactListMenu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.ui.contactDetail.ContactDetailActivity

class ContactListAdapter(private val contactList: MutableSet<Contact>):
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(contactList.elementAt(position))

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ContactDetailActivity()::class.java)
            intent.putExtra("contact", contactList.elementAt(position))
            it.context.startActivity(intent)
/**
 *          Lo mismo:
 *
 *          val intent = Intent(holder.itemView.context, ContactDetailActivity()::class.java)
 *          intent.putExtra("contact", contactList.elementAt(position))
 *          holder.itemView.context.startActivity(intent)
 **/

            Toast.makeText(it.context,
                "You have selected " + contactList.elementAt(position).name,
                Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return contactList.size
    }

}