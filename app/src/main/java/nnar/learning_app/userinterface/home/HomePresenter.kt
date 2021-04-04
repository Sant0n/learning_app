package nnar.learning_app.userinterface.home

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.domain.model.ContactViewHolder
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.domain.model.Contact

class HomePresenter() {

    var mContacts: ArrayList<Contact> = ContactRepository.getContacts()

    fun addContact() {
        val name = "Person " + ++ContactRepository.lastContactId
        val isOnline = true

        mContacts.add(Contact(name, isOnline))
    }

    private fun setButtonState(contactViewHolder: ContactViewHolder, state: Boolean) {
        val text: String = if (state) "Online" else "Offline"
        contactViewHolder.setButtonState(text, state)
    }
}