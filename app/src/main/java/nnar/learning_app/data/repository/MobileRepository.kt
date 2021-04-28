package nnar.learning_app.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.model.CustomResponse
import java.lang.Exception

class MobileRepository {

    private val db = Firebase.firestore

    companion object {
        private val localList = mutableListOf<Mobile>()
    }


    suspend fun getAllMobiles(userID: String): CustomResponse {
        return try {
            val listOfMobiles = mutableListOf<Mobile>()
            val docs = db.collection("mobiles")
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
            db.collection("mobiles").document()
                .set(mobile)
                .await()
            CustomResponse(false, "")
        } catch (e: Exception) {
            CustomResponse(true, e.toString())
        }

    }


    suspend fun removeMobile(mobile: Mobile): CustomResponse {
        return try {
            val docs = db.collection("mobiles").whereEqualTo("name", mobile.name)
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