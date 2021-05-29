package nnar.learning_app.datainterface

import android.content.Context
import android.net.Uri
import nnar.learning_app.domain.model.Contact

interface HomeView {
    fun updateAdapter()
    fun getCurrentUserUID(): String
    fun getContext(): Context
    fun selectPicture()
    fun setDialogContent(contact: Contact)
    fun contactDialog(position: Int?)
    fun setNewContactPicture(uri: Uri)
    fun generateNotificaction()
}