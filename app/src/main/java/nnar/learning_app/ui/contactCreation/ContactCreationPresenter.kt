package nnar.learning_app.ui.contactCreation

import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactUseCase

class ContactCreationPresenter (private val view: ContactCreationView, private val useCase: ContactUseCase){

    internal fun saveContact(){}

    internal fun permissionResult(b:Boolean){
        if(b){
            view.permissionResult("Permission granted")
            view.openGallery()
        }else{
            view.permissionResult("Permission denied")
        }
    }
}