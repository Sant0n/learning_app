package nnar.learning_app.data.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.model.CustomResponse
import java.lang.Exception

class MobileRepository {

    private lateinit var db: DocumentReference

    companion object {
        private val localList = mutableListOf<Mobile>()
    }


    suspend fun getAllMobiles(userID: String): CustomResponse {
        db = Firebase.firestore.collection("users").document(userID)
        return try {
            val listOfMobiles = mutableListOf<Mobile>()
            val docs = db.collection("userMobiles")
                .get()
                .await()

            for (document in docs) {
                listOfMobiles.add(document.toObject())
            }

            localList.addAll(listOfMobiles)
            CustomResponse(false, "")

        } catch (e: Exception) {
            CustomResponse(true, e.toString())
        }

    }


    suspend fun addMobile(mobile: Mobile): CustomResponse {

        return try {
            db.collection("userMobiles")
                .document()
                .set(mobile)
                .await()

            localList.add(mobile)
            CustomResponse(false, "")
        } catch (e: Exception) {
            CustomResponse(true, e.toString())
        }

    }


    suspend fun removeMobile(mobile: Mobile): CustomResponse {
        return try {
            val docs = db.collection("userMobiles")
                .whereEqualTo("name", mobile.name)
                .whereEqualTo("version", mobile.version)
                .get()
                .await()
            for (doc in docs) {
                doc.reference.delete()
            }
            CustomResponse(false, "")
        } catch (e: Exception) {
            CustomResponse(true, e.toString())
        }

    }

    fun getMobileAtPosition(pos: Int) = localList[pos]

    fun getItemCount() = localList.size

    fun removeLocally(position: Int) = localList.removeAt(position)
}