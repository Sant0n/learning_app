package nnar.learning_app.domain.usecase

import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.data.repository.UserRepository

class LoginUserUsecase(private val userRepository: UserRepository) {

    suspend fun checkUserRegistered(userID: String) = userRepository.checkUserRegistered(userID)
    suspend fun registerUser(user: FirebaseUser) = userRepository.registerUser(user)
}