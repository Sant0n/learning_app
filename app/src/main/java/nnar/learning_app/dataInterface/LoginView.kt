package nnar.learning_app.dataInterface

interface LoginView {
    fun showErrorLogin()
    fun loginSuccessful()
    fun googleSuccessful(idToken: String)
    fun googleError()
}