package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface RowView {
    fun seeDetails(contact: Contact)
    fun setButtonState(text: String, state: Boolean)
}
