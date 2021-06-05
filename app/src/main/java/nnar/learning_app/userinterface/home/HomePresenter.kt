package nnar.learning_app.userinterface.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact

/**
 * The presenter for the [HomeActivity].
 * @constructor It receives the [homeView] interface to interact with the activity
 */
class HomePresenter(private val homeView: HomeView) : ViewModel() {
    // Set the attribute
    var selectedPicture: Uri? = null
    private val repository = ContactRepository(homeView.getCurrentUserUID())

    /**
     * Sets the initial set of contacts
     */
    fun setContactList() = viewModelScope.launch {
        if (repository.getCurrentContactsId())
            homeView.updateAdapter()
    }

    /**
     * Returns the number of contacts
     */
    fun getNumberOfContacts() = repository.size()

    /**
     * Removes contact the contact identified by the [position]
     */
    fun removeContact(position: Int) = viewModelScope.launch {
        repository.removeContact(position)
    }

    /**
     * Sets the contact details by using the current [view] and [position] for the contact.
     */
    fun setContact(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        view.setContactView(contact)
    }

    /**
     * Resets the contact list
     */
    fun reset() = repository.reset()

    /**
     * Goes to the Contact Info view by using the current [view] and [position] for the contact.
     */
    fun seeContactDetails(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        val uid = homeView.getCurrentUserUID()
        view.seeMore(contact, uid)
    }

    /**
     * Sets the selected [image] from the gallery when not null
     */
    fun selectedPicture(image: Uri?) {
        selectedPicture = image
        if (image != null) homeView.setNewContactPicture(image)
    }

    /**
     * Checks if the dialog is for editing and sets the contact info based on its [position].
     */
    fun setCurrentContactInfo(position: Int? = null) {
        if (position != null) homeView.setDialogContent(repository.getContact(position))
    }

    /**
     * Checks if gallery permissions were [granted].
     */
    fun checkPermissions(granted: Boolean) {
        if (granted) homeView.selectPicture()
    }

    /**
     * Shows the dialog for the contact at the given [position].
     */
    fun contactDialog(position: Int) = homeView.contactDialog(position)

    /**
     * Set action for Dialog Save button.
     * It will set the [name] and the [state] for the contact, and the [position]
     * will be given when modifying a contact instead of creating it
     */
    fun setPositiveButtonAction(name: String, state: Boolean, position: Int? = null) {
        // Set the new values
        val pic = selectedPicture?.toString() ?: repository.getImageURI(position)
        val contact = Contact(name, state, pic)

        // Add/Modify contact if needed
        viewModelScope.launch {
            if (selectedPicture == null) {
                repository.write(contact, position)
                notifyChanges(position)
            } else if (repository.uploadPicture(selectedPicture!!, contact, position)) {
                selectedPicture = null
                notifyChanges(position)
            }
        }
    }

    /**
     * Notify changes to adapter and launches notification to the user if [position] is not null.
     */
    private fun notifyChanges(position: Int?) {
        homeView.updateAdapter()
        if (position == null)
            homeView.generateNotification()
    }
}