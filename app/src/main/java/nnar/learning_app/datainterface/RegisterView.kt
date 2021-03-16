package nnar.learning_app.datainterface

import android.widget.TextView

interface RegisterView {
    fun showRegisterResponse(s: String)

    fun showErrorNameField(s: String)
    fun showErrorEmailField(s: String)
    fun showErrorPassField(s: String)
    fun showErrorRepeatPassField(s:String)

    fun drawSuccessEmailField()
    fun drawSuccessUsernameField()
    fun drawSuccessPasswordField()
    fun drawSuccessRepeatPasswordField()


    fun navigateToHome()
}