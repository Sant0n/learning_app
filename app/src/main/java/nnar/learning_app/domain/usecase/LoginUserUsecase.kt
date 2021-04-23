package nnar.learning_app.domain.usecase

import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.data.repository.UserRepository

class LoginUserUsecase(private val userRepository: UserRepository) {

    fun loginUser(email: String, password: String) = userRepository.loginUser(email, password)
    fun checkUserRegistered(userID: String) = userRepository.checkUserRegistered(userID)
    fun registerUser(user: FirebaseUser) = userRepository.registerUser(user)
}