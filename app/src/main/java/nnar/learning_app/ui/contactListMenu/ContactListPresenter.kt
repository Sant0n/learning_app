package nnar.learning_app.ui.contactListMenu

import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.model.Contact
import nnar.learning_app.domain.usecase.ContactUseCase


class ContactListPresenter (private val view: ContactListView, private val useCase: ContactUseCase){

    internal fun fetchContacts(): MutableSet<Contact> {
        return useCase.getContacts()
    }

    internal fun selectAContact(){

    }

    internal fun deleteAContact(){

    }

    internal fun addNewContact(){

    }

}