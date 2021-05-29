package nnar.learning_app.dataInterface

import android.net.Uri
import nnar.learning_app.domain.model.Mobile


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
    fun generateNotification(summary: String, mobile: Mobile)
}