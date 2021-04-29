package nnar.learning_app.ui.contactListMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.model.ContactFirestore
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase

//import nnar.learning_app.domain.usecase.ContactUseCase

class ContactListPresenter(private val view: ContactListView, private val useCase: ContactFirestoreUseCase) : ViewModel(){

    internal fun getContacts(){
        viewModelScope.launch {
            val response = useCase.getContactList()
            if(response){
                view.updateData()
            }else{
                view.showMessageErrorContactsUpdated("error getting contacts from repository")
            }
        }
    }

    internal fun addFirebaseContactsFirtstime(){
        viewModelScope.launch {
            useCase.addContactsFirstsTime()
        }
    }

    internal fun addNewContact(){
        val name = "mongolo1"
        val email = "mongolo1@gmail.com"
        val phone = "+34 6969696969"

        viewModelScope.launch {
            if(useCase.addNewContact(name, phone, null, email)){
                view.updateData()
            }else{
                view.showMessageErrorContactsUpdated("error getting contacts from repository")

            }
        }
        view.showMessageContactAdded("Contact $name added")
    }

    internal fun removeContact(contact:ContactFirestore){
        viewModelScope.launch {
            useCase.removeContact(contact)
        }
        view.showMessageContactDeleted("Contact " + contact.name + " deleted")
    }

    internal fun getContact(position:Int) = useCase.getContact(position)

    internal fun getItemCount(): Int = useCase.getItemCount()

}


/** private var contactList: MutableSet<ContactFirestore> = mutableSetOf()

internal fun updateData(cList: MutableSet<ContactFirestore>) {
contactList.clear()
contactList = cList
}

internal fun onBindMobileRowViewAtPosition(position: Int, rowView: ContactViewHolder) {
val contact = contactList.elementAt(position)
rowView.bindData(contact)
}**/