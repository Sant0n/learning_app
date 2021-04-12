package nnar.learning_app.ui.contactListMenu

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nnar.learning_app.R
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.ui.contactDetail.ContactDetailActivity

class ContactListAdapter():
    RecyclerView.Adapter<ContactViewHolder>() {

    private var presenter = ContactListPresenter()

    fun updateData(contactList: MutableSet<Contact>){
        presenter.updateData(contactList)
        notifyDataSetChanged()
    }

    fun addContact(contact: Contact){
        presenter.addContact(contact)
        notifyDataSetChanged()
    }

    private fun deleteContact(contact: Contact){
        presenter.removeContact(contact)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        presenter.onBindMobileRowViewAtPosition(position,holder)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ContactDetailActivity()::class.java)
            intent.putExtra("contact", presenter.getContact(position))
            it.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            //create AlertDialog with Builder
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Do you want to delete " + presenter.getContact(position).name)
                builder.setPositiveButton("DELETE",
                    DialogInterface.OnClickListener { _, _ ->
                        deleteContact(presenter.getContact(position))
                    })
                .setNegativeButton("CANCEL",
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })

            val dialog = builder.create()
            dialog.show()
            true
        }

        /*holder.bindData(contactList.elementAt(position))

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
        }*/
    }

    override fun getItemCount(): Int = presenter.getItemCount()

}