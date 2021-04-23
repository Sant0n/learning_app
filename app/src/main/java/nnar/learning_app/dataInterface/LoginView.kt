package nnar.learning_app.dataInterface

interface LoginView {
    fun showErrorLogin(e: String)
    fun loginSuccessful()
    fun googleSuccessful(idToken: String)
    fun googleError()
    fun moveToHome()
}