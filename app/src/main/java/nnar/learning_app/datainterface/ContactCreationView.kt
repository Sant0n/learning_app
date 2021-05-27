package nnar.learning_app.datainterface

import android.net.Uri

interface ContactCreationView {

    fun showErrorNameField(s: String)
    fun showErrorEmailField(s: String)
    fun showErrorPhoneField(s: String)
    fun showErrorImageField(s:String)

    fun showSuccessImageField()
    fun showSuccessEmailField()
    fun showSuccessNameField()
    fun showSuccessPhoneField()

    fun openGallery()
    fun permissionResult(s:String)
    fun showImage(uri:Uri)
}