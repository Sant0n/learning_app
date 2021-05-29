package nnar.learning_app.userinterface.home

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nnar.learning_app.databinding.ItemContactBinding
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.userinterface.contact.ContactInfoActivity
import android.util.Pair as UtilPair

class ContactViewHolder(private val view: View) : RecyclerView.ViewHolder(view), RowView {
    // Binding for the rows
    private lateinit var uri: Uri
    private var binding = ItemContactBinding.bind(view)

    // Get remove button
    fun getRemoveButton() = binding.removeContact

    // Get contact edit button
    fun getContactEdit() = binding.editContact

    // Get contact edit button
    override fun getContactPicture() = binding.contactPicture

    // Get the context
    override fun getContext(): Context = view.context

    // Get contact name
    override fun getName() = binding.contactName.text.toString()

    // Set contact info
    override fun setContactView(contact: Contact) {
        // Set contact info
        uri = Uri.parse(contact.pic)
        binding.contactName.text = contact.name

        // Load image into resource
        Picasso.get()
            .load(uri)
            .resize(1000, 1000)
            .centerCrop()
            .into(binding.contactPicture)
    }

    // Go to Contact Info Activity
    override fun seeMore(contact: Contact, uid: String) {
        // Set intent for Contact Details
        val intent = Intent(view.context, ContactInfoActivity::class.java)

        // Set contact info
        intent.putExtra("contact", contact)
        intent.putExtra("uid", uid)
        intent.putExtra("uri", uri)

        // Start activity
        val options = ActivityOptions.makeSceneTransitionAnimation(
            view.context as Activity,
            UtilPair.create(binding.contactName, "contact_name"),
            UtilPair.create(binding.contactPicture, "contact_picture")
        )
        view.context.startActivity(intent, options.toBundle())
    }
}