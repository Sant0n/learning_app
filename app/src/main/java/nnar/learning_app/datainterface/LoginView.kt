package nnar.learning_app.datainterface

// Funciones que van el presenter

interface LoginView {

    fun showLoginResponse(s:String)
    fun showErrorNameField(s: String)
    fun showErrorPassField(s: String)
    fun navigateToHome()
}