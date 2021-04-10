package nnar.learning_app.ui.contactListMenu

import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.usecase.ContactUseCase

class ContactListMenuPresenter(private val contactListView: ContactListView, private val useCase:ContactUseCase){

    internal fun fetchContacts() {
        contactListView.getList(useCase.getContacts())
    }

}