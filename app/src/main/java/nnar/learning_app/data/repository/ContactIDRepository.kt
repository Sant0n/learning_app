package nnar.learning_app.data.repository

class ContactIDRepository {

    companion object {
        // Id counter
        var lastContactId: Int = 0
        var ids = ArrayList<Int>()
    }
}