package nnar.learning_app.userinterface.home

import nnar.learning_app.data.repository.ContactRepository

class HomePresenter() {

    fun addContact() {
        ContactRepository.addContact()
    }
}