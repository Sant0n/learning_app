package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface HomeView {
    fun seeDetails(contact: Contact)
}