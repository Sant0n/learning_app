package nnar.learning_app.datainterface

interface ContactListView {

    fun updateData()
    fun showMessageContactAdded(s:String)
    fun showMessageContactDeleted(s:String)
    fun showMessageErrorContactsUpdated(s:String)
    //fun navigateToContactDetail()
}