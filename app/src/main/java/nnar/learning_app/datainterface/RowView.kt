package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface RowView {
    fun getName(): String
    fun getState(): Boolean
    fun seeMore(contact: Contact)
    fun setContactView(contact: Contact)
    fun setButtonState(contact: Contact)
}
