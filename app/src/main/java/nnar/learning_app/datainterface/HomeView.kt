package nnar.learning_app.datainterface

import android.content.Context
import android.net.Uri
import nnar.learning_app.domain.model.Contact

interface HomeView {
    /**
     * Notify dataset change to the adapter
     */
    fun updateAdapter()

    /**
     * Returns the UID form current user
     */
    fun getCurrentUserUID(): String

    /**
     * Returns the Home Activity context
     */
    fun getContext(): Context

    /**
     * Opens the gallery to look for the picture to upload
     */
    fun selectPicture()

    /**
     * Set Dialog content where [contact] contains the information to set the dialog
     */
    fun setDialogContent(contact: Contact)

    /**
     * Show Alert Dialog to get new input. [position] indicates the contact to modify
     * or null if it is a new contact.
     */
    fun contactDialog(position: Int?)

    /**
     * Sets the new Picasso image by receiving the [uri] for the new picture
     */
    fun setNewContactPicture(uri: Uri)

    /**
     * Generate new added user notification
     */
    fun generateNotification()
}