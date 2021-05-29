package nnar.learning_app.userinterface.contact

class ContactInfoPresenter() {
    // Get state full name
    fun getStateFullName(state: Boolean) = if (state) "Online" else "Offline"
}