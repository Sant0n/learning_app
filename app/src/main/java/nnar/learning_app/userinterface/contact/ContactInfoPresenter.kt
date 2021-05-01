package nnar.learning_app.userinterface.contact

import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.datainterface.ContactInfoView

@ExperimentalCoroutinesApi
class ContactInfoPresenter(private val contactInfoView: ContactInfoView) {
    // Set the Firebase Repository
    private val repository = ContactRepository(contactInfoView.getCurrentUserUID())

    // Get random picture for contact
    fun setImage() {
        // Get the attributes
        val uri = repository.getImageURI()
        val image = contactInfoView.getContactPic()

        // Load image into resource
        Picasso.get().load(uri).resize(1000, 1000)
            .centerCrop().into(image)
    }

    // Get state full name
    fun getStateFullName(state: Boolean) = if (state) "Online" else "Offline"
}