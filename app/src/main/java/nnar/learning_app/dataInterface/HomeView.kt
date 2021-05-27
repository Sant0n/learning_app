package nnar.learning_app.dataInterface

import android.net.Uri


interface HomeView {

    fun updateData()
    fun showRemoveButton()
    fun hideRemoveButton()
    fun showAddButton()
    fun hideAddButton()
    fun clearSearchFocus()
    fun openGallery()
    fun showNameNotFilled()
    fun showVersionNotFilled()
    fun clearFieldsError()
    fun changeDialogImage(uri: Uri)
    fun dismissDialog()
}