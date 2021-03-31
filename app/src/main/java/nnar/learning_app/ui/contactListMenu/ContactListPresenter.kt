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
        val name: String = "mongolo1"
        val email: String = "mongolo1@gmail.com"
        val phone: String = "+34 6969696969"

        val message = useCase.addNewContact(name, phone, null, email)
        view.showMessageContactAdded(message)
    }

}