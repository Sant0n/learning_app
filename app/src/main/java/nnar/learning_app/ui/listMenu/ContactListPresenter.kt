package nnar.learning_app.ui.listMenu

import nnar.learning_app.datainterface.ContactListView
import nnar.learning_app.domain.usercase.ContactUseCase


class ContactListPresenter (private val view: ContactListView, private val useCase: ContactUseCase){
}