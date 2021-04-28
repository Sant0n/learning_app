package nnar.learning_app.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import nnar.learning_app.domain.model.CustomResponse
import java.lang.Exception

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun checkUserRegistered(userID: String): CustomResponse {
        val doc = db.collection("users")
            .document(userID)
            .get()
            .await()

        return if (doc.exists()) CustomResponse(false, "") else CustomResponse(
            true,
            "User not registered"
        )
    }

    suspend fun registerUser(user: FirebaseUser): CustomResponse {
        return try {
            val fbUser = hashMapOf(
                "email" to user.email,
                "name" to user.displayName,
                "userId" to user.uid
            )

            db.collection("users")
                .document(user.uid.toString())
                .set(fbUser)
                .await()

            CustomResponse(false, "")
        } catch (e: Exception) {
            CustomResponse(true, e.toString())
        }
    }
}