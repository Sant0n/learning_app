package nnar.learning_app.ui.contactListMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.model.ContactFirestore
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase


class ContactListPresenter(private val view: ContactListView, private val useCase: ContactFirestoreUseCase) : ViewModel(){

    internal fun getContacts(userUID: String){
        viewModelScope.launch {
            val response = useCase.getContactList(userUID)
            if(response){
                view.updateData()
            }else{
                view.showMessageErrorContactsUpdated("error getting contacts from repository")
            }
        }
    }

    /*internal fun addFirebaseContactsFirstsTime(userUID: String){
        viewModelScope.launch {
            if(useCase.addContactsFirstsTime(userUID)){
                view.updateData()
            }
            else{
                view.showMessageErrorContactsUpdated("error getting contacts from repository")
            }
        }
    }*/

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
            if(useCase.removeContact(contact)){
                view.updateData()
            }else{
                view.showMessageErrorContactsUpdated("error deleting contacts from repository")
            }
        }
        view.showMessageContactDeleted("Contact " + contact.name + " deleted")
    }

    internal fun getContact(position:Int) = useCase.getContact(position)

    internal fun getItemCount(): Int = useCase.getItemCount()

    internal fun deleteLocalData(){
        useCase.deleteLocalData()
    }
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