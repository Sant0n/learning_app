package nnar.learning_app.domain.usecase

import android.net.Uri
import nnar.learning_app.data.repository.ContactFirestoreRepository
import nnar.learning_app.domain.model.ContactFirestore


class ContactFirestoreUseCase(private val contactFirestoreRepository: ContactFirestoreRepository) {

    //suspend fun addContactsFirstsTime(userUID: String) = contactFirestoreRepository.writeDataOnFirestoreFirstsTime(userUID)

    suspend fun addNewContact(name:String, phoneNumber:String, image:String?, email:String) =
        contactFirestoreRepository.writeDataOnFirestore(contactFirestoreRepository.createContact(name, phoneNumber, image, email))

    suspend fun removeContact(contact: ContactFirestore) = contactFirestoreRepository.removeContactFirestore(contact)

    suspend fun getContactList(userUID: String) = contactFirestoreRepository.readContactsFirestore(userUID)

    suspend fun addContact(image:Uri, name:String, email:String, phone:String) =
        contactFirestoreRepository.addContact(image, name, email, phone)

    suspend fun uploadImage(imageName:String, image: Uri) = contactFirestoreRepository.uploadImage(imageName, image)

    fun getContact(position: Int) = contactFirestoreRepository.getContact(position)

    fun getItemCount() = contactFirestoreRepository.getItemCount()

    fun deleteLocalData() = contactFirestoreRepository.deleteLocalData()
}
