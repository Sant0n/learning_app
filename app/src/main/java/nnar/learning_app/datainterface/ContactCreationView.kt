package nnar.learning_app.datainterface

interface ContactCreationView {

    fun showErrorNameField(s: String)
    fun showErrorEmailField(s: String)
    fun showErrorPhoneField(s: String)

    fun showSuccessImageField()
    fun showSuccessEmailField()
    fun showSuccessNameField()
    fun showSuccessPhoneField()

    fun openGallery()
    fun permissionResult(s:String)
}