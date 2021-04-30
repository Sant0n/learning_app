package nnar.learning_app.ui.contactListMenu

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import nnar.learning_app.R
import nnar.learning_app.ui.contactDetail.ContactDetailActivity

//import nnar.learning_app.domain.model.Contact

class ContactListAdapter(private var presenter: ContactListPresenter) :
    RecyclerView.Adapter<ContactViewHolder>() {

    fun updateData(){
        notifyDataSetChanged()
    }

    fun deleteLocalData(){
        presenter.deleteLocalData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_row, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int = presenter.getItemCount()

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        val contact = presenter.getContact(position)
        holder.bindData(contact)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ContactDetailActivity()::class.java)
            intent.putExtra("contact", presenter.getContact(position))
            it.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Do you want to delete " + presenter.getContact(position).name + "?")
                builder.setPositiveButton("DELETE"
                ) { _, _ ->
                    presenter.removeContact(presenter.getContact(position))
                }
                    .setNegativeButton("CANCEL"
                    ) { dialog, _ ->
                        dialog.cancel()
                    }

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

}