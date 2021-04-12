package nnar.learning_app.ui.contactListMenu

import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.usecase.ContactUseCase


class ContactListHomePresenter (private val view: ContactListView, private val useCase: ContactUseCase){

    internal fun fetchContacts() {
        view.getList(useCase.getContacts())
    }

    /*internal fun deleteAContact(contact: Contact){
        useCase.deleteAContact(contact)
        view.showMessageContactDeleted("Contact " + contact.name + " deleted")
    }*/

    internal fun addNewContact(){
        val name = "mongolo1"
        val email = "mongolo1@gmail.com"
        val phone = "+34 6969696969"

        useCase.addNewContact(name, phone, null, email)
        view.showMessageContactAdded("Contact $name added")
    }

}