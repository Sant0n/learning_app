package nnar.learning_app.datainterface

import nnar.learning_app.domain.model.Contact

interface ContactListRowView {
    /**
     * Si se pone llevas al final de la declaracion, se puede hacer algo obligatorio e el override de la funcion y se llama al super de la funcion
     * **/
    fun createRow(contact: Contact)
}