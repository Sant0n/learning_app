package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.Contact

class ContactRepository {

    companion object {
        // Set control for the IDs
        private var lastContactId: Int = 0
        private var contacts = createContactsList(6)

        // Reset repository
        fun reset() {
            lastContactId = 0
            contacts = createContactsList(6)
        }

        // Create new contact
        private fun createContact(state: Boolean = true): Contact {
            val name: String = "Person " + ++lastContactId
            return Contact(name, state)
        }

        // Set the initial contacts
        private fun createContactsList(numContacts: Int): ArrayList<Contact> {
            // Set empty list
            val contacts = ArrayList<Contact>()

            // Set the default values for the contacts
            for (i in 1..numContacts) {
                val contact = createContact(i <= numContacts / 2)
                contacts.add(contact)
                //firebaseDatabase.write(contact)
            }

            // Return the initial contacts
            return contacts
        }
    }
}