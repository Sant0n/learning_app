package nnar.learning_app.ui.contactListMenu

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import nnar.learning_app.databinding.ContactListRowBinding
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.domain.model.ContactFirestore

class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView) {

    private val binding: ContactListRowBinding = ContactListRowBinding.bind(contactView)

    private val name: TextView = binding.contactRowName
    private val image: ImageView = binding.contactRowImage

    fun bindData(contact: ContactFirestore){
        name.text = contact.name
        image.setImageResource(contact.image)
    }

}