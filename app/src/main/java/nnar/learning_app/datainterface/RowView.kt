package nnar.learning_app.datainterface

import android.content.Context
import android.widget.ImageView
import nnar.learning_app.domain.model.Contact

interface RowView {
    /**
     * Get contact name
     */
    fun getName(): String

    /**
     * Go to Contact Info Activity
     */
    fun seeMore(contact: Contact, uid: String)

    /**
     * Set contact info
     */
    fun setContactView(contact: Contact)

    /**
     * Get the context
     */
    fun getContext(): Context

    /**
     * Get contact edit button
     */
    fun getContactPicture(): ImageView
}
