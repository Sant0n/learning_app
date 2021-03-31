package nnar.learning_app.data.repository

import nnar.learning_app.R
import nnar.learning_app.domain.model.Contact

class ContactRepository {

    private val contactSet: MutableSet<Contact> = mutableSetOf(
        Contact(1, "raul", "+34 11111111111", R.drawable.avataaars1, "raul@gmail.com"),
        Contact(2, "sigrit", "+34 2222222222", R.drawable.avataaars2, "sigrit@gmail.com"),
        Contact(3, "nathan", "+34 3333333333", R.drawable.avataaars3, "nathan@gmail.com"),
        Contact(4, "alberto", "+34 4444444444", R.drawable.avataaars4, "alberto@gmail.com"),
        Contact(5, "nicole", "+34 5555555555", R.drawable.avataaars5, "nicole@gmail.com"),
        Contact(6, "agus", "+34 666666666", R.drawable.avataaars6, "agus@gmail.com")
    )

    fun addNewContact(name:String, phoneNumber:String, image:Int?, email:String): String {
        val newId = contactSet.size + 1
        val auxImage = image ?: R.drawable.avataaars_default // If image is null then default
        val newContact = Contact(newId, name, phoneNumber, auxImage, email)
        contactSet.add(newContact)
        print(contactSet)
        return "Contact added"
    }

    fun deleteAContact(){
        
    }

    fun getContacts() = contactSet

}