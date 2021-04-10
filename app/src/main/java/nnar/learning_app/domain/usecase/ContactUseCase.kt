package nnar.learning_app.domain.usecase

import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.domain.model.Contact

class ContactUseCase (private val contactRepository: ContactRepository){

    fun addNewContact(name:String, phoneNumber:String, image:Int?, email:String) =
        contactRepository.addNewContact(name, phoneNumber, image, email)

    fun deleteAContact(contact: Contact) = contactRepository.deleteAContact(contact)

    fun getContacts() = contactRepository.getContacts()
}