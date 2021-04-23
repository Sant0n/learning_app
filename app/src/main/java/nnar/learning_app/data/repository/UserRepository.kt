package nnar.learning_app.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.model.UserResponse

class UserRepository {

    private val db = FirebaseFirestore.getInstance()

    private val listOfUsers: MutableList<User> = arrayListOf(
        User("93agusmartin@gmail.com", "agus69", "pollote"),
        User("nicolegp94@gmail.com", "nicky", "chocho"),
        User("nathan.crengifo@gmail.com", "nathaniel", "pervertido"),
        User("elderaul@gmail.com", "raul", "valdilecha")
    )

    fun loginUser(userId: String, pass: String): UserResponse {
        for (user in listOfUsers) {
            if ((user.email == userId && user.password == pass) || (user.username == userId && user.password == pass)) {
               return  UserResponse(false, "Login successful")
            }
        }
        return UserResponse(true, "Credentials not valid")
    }

    fun checkUserRegistered(userID: String) = db.collection("users").document(userID)

    fun registerUser(user: FirebaseUser): Task<Void> {
        val fbUser = hashMapOf(
            "email" to user.email,
            "name" to user.displayName,
            "userId" to user.uid)
        return db.collection("users").document(user.uid.toString())
            .set(fbUser)
    }
}