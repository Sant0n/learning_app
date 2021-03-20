package nnar.learning_app.data.repository

class ContactRepository(val name: String, val isOnline: Boolean) {

    companion object {
        private var lastContactId = 0
        fun createContactsList(numContacts: Int): ArrayList<ContactRepository> {
            val contacts = ArrayList<ContactRepository>()
            for (i in 1..numContacts) {
                contacts.add(ContactRepository("Person " + ++lastContactId, i <= numContacts / 2))
            }
            return contacts
        }
    }
}