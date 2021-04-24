package nnar.learning_app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import nnar.learning_app.domain.model.Mobile

class MobileRepository {


    private val db = FirebaseFirestore.getInstance()

    fun getAllMobiles() = db.collection("mobiles")

    fun addMobile() = db.collection("mobiles").document()

    fun removeMobile(mobile: Mobile) = db.collection("mobiles").whereEqualTo("name", mobile.name)
        .whereEqualTo("version", mobile.version).get()
}