package nnar.learning_app.datainterface

interface LoginView {
    fun showErrorLogin()
    fun loginSuccessful()
    fun firebaseAuthWithGoogle(idToken: String)
}