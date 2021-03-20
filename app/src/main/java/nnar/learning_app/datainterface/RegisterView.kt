package nnar.learning_app.datainterface

interface RegisterView {
    fun showSuccessfulEmailBox()
    fun showErrorEmailBox()
    fun showSuccessfulPassBox()
    fun showErrorPassBox()
    fun registerSuccessful()
    fun registerError()
}