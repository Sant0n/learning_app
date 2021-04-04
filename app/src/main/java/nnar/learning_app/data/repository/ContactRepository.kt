package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.Contact

class ContactRepository {

    companion object {
        // Set control for the IDs
        var lastContactId = 0
        private var contacts = createContactsList(6)

        // Get companion contacts
        fun getContacts() = contacts

        // Set the initial contacts
        private fun createContactsList(numContacts: Int): ArrayList<Contact> {
            // Set empty list
            val contacts = ArrayList<Contact>()

            // Set the default values for the contacts
            for (i in 1..numContacts) {
                val name: String = "Person " + ++ContactRepository.lastContactId
                val state: Boolean = i <= numContacts / 2
                contacts.add(Contact(name, state))
            }

            // Return the initial contacts
            return contacts
        }
    }
}