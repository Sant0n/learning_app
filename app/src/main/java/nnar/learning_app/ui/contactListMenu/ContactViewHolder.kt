package nnar.learning_app.ui.contactListMenu

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

import nnar.learning_app.databinding.ContactListRowBinding
import nnar.learning_app.domain.model.ContactFirestore
import nnar.learning_app.utils.GlideApp

class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView) {

    private val binding: ContactListRowBinding = ContactListRowBinding.bind(contactView)
    private val storageRef = Firebase.storage.reference

    private val name: TextView = binding.contactRowName
    private val image: ImageView = binding.contactRowImage

    fun bindData(contact: ContactFirestore){
        name.text = contact.name
        loadImage(contact.image)
    }

    private fun loadImage(url:String){
        GlideApp.with(itemView.context)
            .load(storageRef.child(url))
            .into(image)
    }

}