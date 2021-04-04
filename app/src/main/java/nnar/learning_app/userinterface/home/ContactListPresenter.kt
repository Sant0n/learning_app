package nnar.learning_app.userinterface.home;

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.domain.model.ContactViewHolder

class ContactListPresenter(private val view: HomeView) {
    // Get number of contacts
    fun getNumberOfContacts(): Int {
        return ContactRepository.size()
    }

    // Remove contact
    fun removeContact(position: Int) {
        ContactRepository.removeContact(position)
    }

    // Get contact name
    fun getContactName(position: Int): String {
        return ContactRepository.getContactName(position)
    }

    // Set contact information
    fun setButtonState(contactViewHolder: ContactViewHolder, position: Int, change: Boolean = true) {
        // Set button current state
        val state: Boolean = if(change) setButtonState(position) else getContactState(position)
        val stateText: String = getButtonStateText(position)
        contactViewHolder.setButtonState(stateText, state)
    }

    // See contact information
    fun seeContactDetails(position: Int) {
        view.seeDetails(ContactRepository.getContact(position))
    }

    // Get contact state text
    private fun getButtonStateText(position: Int): String {
        return ContactRepository.getStateText(position)
    }

    // Change contact state
    private fun setButtonState(position: Int): Boolean {
        return ContactRepository.setState(position)
    }

    // Get contact state
    private fun getContactState(position: Int): Boolean {
        return ContactRepository.getContactState(position)
    }
}
