package nnar.learning_app.userinterface.home;

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.datainterface.HomeView
import nnar.learning_app.domain.model.Contact

class ContactListPresenter(private val view: HomeView) {

    var mContacts: ArrayList<Contact> = ContactRepository.getContacts()

    fun getNumberOfContacts(): Int {
        return mContacts.size
    }

    fun removeContact(position: Int) {
        mContacts.removeAt(position)
    }

    fun getButtonState(state: Boolean): String {
        return if (state) "Online" else "Offline"
    }

    fun seeContactDetails(contact: Contact) {
        view.seeDetails(contact)
    }
}
