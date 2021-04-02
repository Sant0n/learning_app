package nnar.learning_app.datainterface

import nnar.learning_app.data.repository.ContactRepository

interface HomeView {
    fun seeDetails(contact: ContactRepository)
    fun contactRemoved(position: Int)
}