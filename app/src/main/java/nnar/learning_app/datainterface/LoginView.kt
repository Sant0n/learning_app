package nnar.learning_app.datainterface

interface LoginView {

    fun showLoginResponse(s:String)
    fun showErrorNameField(s: String)
    fun showErrorPassField(s: String)
    fun drawSuccessUsernameField()
    fun drawSuccessPasswordField()
    fun navigateToHome()
    fun googleErrorLogin()
    fun googleSuccessful(idToken: String)
    fun successLogin()
}