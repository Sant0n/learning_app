package nnar.learning_app.datainterface

interface LoginView {
    /**
     * Shows error message when login has failed
     */
    fun showErrorLogin()

    /**
     * Goes to the Main Activity when login has been successful
     */
    fun loginSuccessful()

    /**
     * Authenticates the given [idToken] for the user
     */
    fun firebaseAuthWithGoogle(idToken: String)
}