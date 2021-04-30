package nnar.learning_app.domain.usecase

import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.data.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {

    fun loginUser(username: String, password: String) = userRepository.loginUser(username, password)
    suspend fun checkUserGoogleRegistered(user:FirebaseUser) = userRepository.checkUserGoogleRegistered(user)

}