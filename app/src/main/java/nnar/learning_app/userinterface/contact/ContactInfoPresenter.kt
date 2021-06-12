package nnar.learning_app.userinterface.contact

/**
 * The presenter for the [ContactInfoActivity].
 * @constructor Creates an empty constructor.
 */
class ContactInfoPresenter() {
    /**
     *  Get the state full name
     */
    fun getStateFullName(state: Boolean) = if (state) "Online" else "Offline"
}