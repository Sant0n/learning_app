package nnar.learning_app.userinterface.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.datainterface.RowView
import nnar.learning_app.domain.model.Contact

class HomePresenter(private val homeView: HomeView) : ViewModel() {
    // Set the attribute
    var selectedPicture: Uri? = null
    private val repository = ContactRepository(homeView.getCurrentUserUID())

    // Get initial set of contacts
    fun setContactList() = viewModelScope.launch {
        if (repository.getCurrentContactsId())
            homeView.updateAdapter()
    }

    // Get number of contacts
    fun getNumberOfContacts() = repository.size()

    // Remove contact
    fun removeContact(position: Int) = repository.removeContact(position)

    // Set contact details
    fun setContact(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        view.setContactView(contact)
    }

    // Reset contacts list
    fun reset() = repository.reset()

    // See contact information
    fun seeContactDetails(view: RowView, position: Int) {
        val contact = repository.getContact(position)
        val uid = homeView.getCurrentUserUID()
        view.seeMore(contact, uid)
    }

    // Set selected picture
    fun selectedPicture(image: Uri?) {
        selectedPicture = image
        if (image != null) homeView.setNewContactPicture(image)
    }

    // Check if dialog is for editing
    fun setCurrentContactInfo(position: Int? = null) {
        if (position != null) homeView.setDialogContent(repository.getContact(position))
    }

    // Check gallery permissions
    fun checkPermissions(granted: Boolean) {
        if (granted) homeView.selectPicture()
    }

    // Invoke dialog
    fun invokeDialog(position: Int) {
        homeView.contactDialog(position)
    }

    // Set action for Dialog Save
    fun setPositiveButtonAction(name: String, state: Boolean, position: Int? = null) {
        // Set the new values
        val pic = selectedPicture?.toString() ?: repository.getImageURI(position)
        val contact = Contact(name, state, pic)

        // Add/Modify contact if needed
        viewModelScope.launch {
            if (selectedPicture == null) {
                repository.write(contact, position)
                notifyChanges(position)
            } else {
                if (repository.uploadPicture(selectedPicture!!, contact, position)) {
                    selectedPicture = null
                    notifyChanges(position)
                }
            }
        }
    }

    // Notify changes to adapter and user
    private fun notifyChanges(position: Int?) {
        homeView.updateAdapter()
        if (position == null)
            homeView.generateNotificaction()
    }
}