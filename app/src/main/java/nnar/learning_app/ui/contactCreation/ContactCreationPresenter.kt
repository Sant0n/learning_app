package nnar.learning_app.ui.contactCreation

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase


class ContactCreationPresenter (private val view: ContactCreationView, private val useCase: ContactFirestoreUseCase): ViewModel(){

    val selectedImage: Uri? = null

    internal fun saveContact(){

    }

    internal fun verifyImage(image:Drawable, hasFocus: Boolean){

    }

    internal  fun verifyName(name: String, hasFocus:Boolean, isNotBlank:Boolean){
        if(!hasFocus && isNotBlank) {
            view.showSuccessNameField()
        }else{
            view.showErrorNameField("Use a correct name")
        }
    }

    internal fun verifyEmail(email:String, hasFocus:Boolean, isNotBlank: Boolean){

        if(!hasFocus && isNotBlank) {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                view.showSuccessEmailField()
            }else{
                view.showErrorEmailField("Wrong email format")
            }
        }

    }
    internal fun verifyPhone(phone:String, hasFocus:Boolean, isNotBlank:Boolean){
        if(!hasFocus && isNotBlank) {
            view.showSuccessPhoneField()
        }else{
            view.showErrorNameField("Use a correct name")
        }
    }

    internal fun verifyFormFields(image: Uri, name:String, email:String, phone:String){
        viewModelScope.launch {
            val imageName = "avatar_$name"
            val response = useCase.uploadImage(imageName, image)
            if(response){
                view.permissionResult("Image added")
            }else{
                view.permissionResult("Error in add the image")
            }
        }
        //useCase.addNewContact()
    }

    internal fun permissionResult(b:Boolean){
        if(b){
            view.permissionResult("Permission granted")
            view.openGallery()
        }else{
            view.permissionResult("Permission denied")
        }
    }
}