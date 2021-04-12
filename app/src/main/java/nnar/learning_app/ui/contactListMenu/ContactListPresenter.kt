package nnar.learning_app.ui.contactListMenu

import nnar.learning_app.domain.model.Contact

class ContactListPresenter{

    private var contactList: MutableSet<Contact> = mutableSetOf()

    internal fun updateData(cList: MutableSet<Contact>) {
        contactList.clear()
        contactList = cList
    }

    internal fun getItemCount(): Int = contactList.size

    internal fun onBindMobileRowViewAtPosition(position: Int, rowView: ContactViewHolder) {
        val contact = contactList.elementAt(position)
        rowView.bindData(contact)
    }

    internal fun getContact(position:Int) = contactList.elementAt(position)

    internal fun addContact (contact: Contact){
        contactList.add(contact)
    }

    internal fun removeContact(contact:Contact){
        contactList.remove(contact)
    }

}