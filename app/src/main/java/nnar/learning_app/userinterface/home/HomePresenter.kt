package nnar.learning_app.userinterface.home

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.data.repository.ContactViewHolder
import nnar.learning_app.datainterface.HomeView

class HomePresenter(private val view: HomeView) {

    private var mContacts = ContactRepository.createContactsList(6)

    fun onBindRepositoryRowViewAtPosition(contactViewHolder: ContactViewHolder, position: Int) {
        // Get the data model based on position
        val contact: ContactRepository = mContacts[position]

        // Set item views based on your views and data model
        contactViewHolder.setNameTextView(contact.name)

        // Set button text
        setButtonState(contactViewHolder, contact.isOnline)

        // Set listeners
        setListeners(contactViewHolder, contact, position)
    }

    fun getNumberOfContacts(): Int {
        return mContacts.size
    }

    fun addContact() {
        val name = "Person " + ++ContactRepository.lastContactId
        val isOnline = true

        mContacts.add(ContactRepository(name, isOnline))
    }

    fun removeContact(position: Int) {
        mContacts.removeAt(position)
    }

    private fun setListeners(contactViewHolder: ContactViewHolder, contact: ContactRepository, position: Int) {
        // Set listener for status change
        contactViewHolder.getStateButton().setOnClickListener {
            contact.isOnline = !contact.isOnline
            setButtonState(contactViewHolder, contact.isOnline)
        }

        // Set listener for contact removal
        contactViewHolder.getRemoveButton().setOnClickListener{
            removeContact(position)
            view.contactRemoved(position)
        }

        // See contact details
        contactViewHolder.getSeeMore().setOnClickListener{
            view.seeDetails(contact)
        }
    }

    private fun setButtonState(contactViewHolder: ContactViewHolder, state: Boolean) {
        val text: String = if (state) "Online" else "Offline"
        contactViewHolder.setButtonState(text, state)
    }
}