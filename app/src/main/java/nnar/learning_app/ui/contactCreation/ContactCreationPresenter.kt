package nnar.learning_app.ui.contactCreation

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase


class ContactCreationPresenter(
    private val view: ContactCreationView,
    private val useCase: ContactFirestoreUseCase
) : ViewModel() {

    internal fun verifyImage(image: Uri?) {
        if (image != null) {
            view.showImage(image)
        }
    }

    internal fun verifyName(name: String, hasFocus: Boolean, isNotBlank: Boolean) {
        if (!hasFocus && isNotBlank) {
            view.showSuccessNameField()
        } else {
            view.showErrorNameField("Use a correct name")
        }
    }

    internal fun verifyEmail(email: String, hasFocus: Boolean, isNotBlank: Boolean) {

        if (!hasFocus && isNotBlank) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                view.showSuccessEmailField()
            } else {
                view.showErrorEmailField("Wrong email format")
            }
        }

    }

    internal fun verifyPhone(phone: String, hasFocus: Boolean, isNotBlank: Boolean) {
        if (!hasFocus && isNotBlank) {
            view.showSuccessPhoneField()
        } else {
            view.showErrorPhoneField("Use a correct phone")
        }
    }

    internal fun saveContact(image: Uri?, name: String, email: String, phone: String) {
        if (name.isEmpty()) {
            view.showErrorNameField("Write a name")
        }
        if (email.isEmpty()) {
            view.showErrorEmailField("Write an email")
        }
        if (phone.isEmpty()) {
            view.showErrorPhoneField("Write a phone")
        } else {
            if (image != null) {
                view.showSuccessImageField()
                viewModelScope.launch {
                    val response = useCase.addContact(image, name, email, phone)
                    if (response) {
                        view.permissionResult("Image added")
                        view.createNotification()
                        view.finishActivity()
                    } else {
                        view.permissionResult("Error in add the image")
                    }
                }
            } else {
                view.showErrorImageField("Image is needed")
            }
        }
    }

    internal fun permissionResult(b: Boolean) {
        if (b) {
            view.permissionResult("Permission granted")
            view.openGallery()
        } else {
            view.permissionResult("Permission denied")
        }
    }
}