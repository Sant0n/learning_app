package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.Contact

class ContactRepository {

    companion object {
        // Set control for the IDs
        private var lastContactId: Int = 0
        private var contacts = createContactsList(6)

        // Get companion contacts
        fun getContacts() = contacts

        // Get contact name
        fun getContactName(position: Int) = contacts[position].name

        // Get button state
        fun getContactState(position: Int): Boolean = contacts[position].isOnline

        // Get state text
        fun getStateText(position: Int) = if (contacts[position].isOnline) "Online" else "Offline"

        // Size of the list
        fun size() = contacts.size

        // Get specific contact
        fun getContact(position: Int) = contacts[position]

        // Change state
        fun setState(position: Int): Boolean {
            contacts[position].isOnline = !contacts[position].isOnline
            return getContactState(position)
        }

        // Delete contact
        fun removeContact(position: Int) {
            contacts.removeAt(position)
        }

        // Add new contact
        fun addContact(state: Boolean = true) {
            contacts.add(createContact(state))
        }

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
                contacts.add(createContact(i <= numContacts / 2))
            }

            // Return the initial contacts
            return contacts
        }
    }
}