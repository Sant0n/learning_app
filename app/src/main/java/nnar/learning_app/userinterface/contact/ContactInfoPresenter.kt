package nnar.learning_app.userinterface.contact

import kotlinx.coroutines.ExperimentalCoroutinesApi
import nnar.learning_app.datainterface.ContactInfoView

@ExperimentalCoroutinesApi
class ContactInfoPresenter() {
    // Get state full name
    fun getStateFullName(state: Boolean) = if (state) "Online" else "Offline"
}