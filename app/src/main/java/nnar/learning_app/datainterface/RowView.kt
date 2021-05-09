package nnar.learning_app.datainterface

import android.content.Context
import android.widget.ImageView
import nnar.learning_app.domain.model.Contact

interface RowView {
    fun getName(): String
    fun seeMore(contact: Contact, uid: String)
    fun setContactView(contact: Contact)
    fun getContext(): Context
    fun getContactPicture(): ImageView
}
