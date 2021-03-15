package nnar.learning_app.datainterface

//Funciones del Activity

interface RegisterView {
    fun showRegisterResponse(s: String)
    fun showErrorNameField(s: String)
    fun showErrorPassField(s: String)
    fun navigateToHome()
}