package nnar.learning_app.datainterface

import android.content.Context
import nnar.learning_app.domain.model.Contact

interface RowView {
    fun getName(): String
    fun getState(): Boolean
    fun seeMore(contact: Contact, uid: String)
    fun setContactView(contact: Contact)
    fun setButtonState(contact: Contact)
    fun getContext(): Context
}
