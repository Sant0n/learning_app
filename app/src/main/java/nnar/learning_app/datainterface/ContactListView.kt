package nnar.learning_app.datainterface

interface ContactListView {

    fun showMessageContactAdded(s:String)
    fun showMessageContactDeleted(s:String)
    fun navigateToContactDetail()

}