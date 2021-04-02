package nnar.learning_app.data.repository

import java.io.Serializable

class ContactRepository(val name: String, var isOnline: Boolean): Serializable{

    companion object {
        var lastContactId = 0
        fun createContactsList(numContacts: Int): ArrayList<ContactRepository> {
            val contacts = ArrayList<ContactRepository>()
            for (i in 1..numContacts) {
                contacts.add(ContactRepository("Person " + ++lastContactId, i <= numContacts / 2))
            }
            return contacts
        }
    }
}