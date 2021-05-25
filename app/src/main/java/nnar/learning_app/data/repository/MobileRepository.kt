package nnar.learning_app.data.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.model.CustomResponse
import nnar.learning_app.domain.model.MobileResponse
import java.lang.Exception

class MobileRepository {

    private lateinit var db: DocumentReference
    private val storageRef = Firebase.storage.reference

    companion object {
        private val localList = mutableListOf<Mobile>()
    }


    suspend fun getAllMobiles(userID: String): CustomResponse {
        return withContext(Dispatchers.IO) {
            db = Firebase.firestore.collection("users").document(userID)
            try {
                val listOfMobiles = mutableListOf<Mobile>()
                val docs = db.collection("userMobiles")
                    .get()
                    .await()

                for (document in docs) {
                    val mobile: Mobile = document.toObject()
                    listOfMobiles.add(mobile)
                }

                localList.addAll(listOfMobiles)
                CustomResponse(false, "")

            } catch (e: Exception) {
                CustomResponse(true, e.toString())
            }
        }
    }


    suspend fun addMobile(mobile: Mobile): CustomResponse {
        return withContext(Dispatchers.IO) {
            try {
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
    }


    suspend fun removeMobile(mobile: Mobile): CustomResponse {
        return withContext(Dispatchers.IO){
            try {
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
    }

    fun getMobileAtPosition(pos: Int) = localList[pos]

    fun getItemCount() = localList.size

    fun removeLocally(position: Int) = localList.removeAt(position)

    /**
    private fun getMobileResponse(mobile: Mobile) = MobileResponse(
        storageRef.child(mobile.img_url),
        mobile.name,
        mobile.version,
        mobile.favorite
    )*/
}