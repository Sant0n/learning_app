package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface ContactListView {

    fun getList(contactList: MutableSet<Contact>)
    fun showMessageContactAdded(s:String)
    fun showMessageContactDeleted(s:String)
    fun navigateToContactDetail()

}