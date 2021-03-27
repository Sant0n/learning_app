package nnar.learning_app.domain.usercase

import nnar.learning_app.data.repository.ContactRespository

class ContactUseCase (private val contactRespository: ContactRespository){

    fun addNewContact(name:String, phoneNumber:String, image:Int?, email:String) =
        contactRespository.addNewContact(name, phoneNumber, image, email)

}