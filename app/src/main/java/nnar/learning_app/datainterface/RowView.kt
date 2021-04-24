package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface RowView {
    fun getName(): String
    fun getState(): Boolean
    fun seeMore(contact: Contact)
    fun setButtonState(text: String, state: Boolean)
    fun setNameTextView(text: String)
    fun setStateView(state: Boolean)
}
