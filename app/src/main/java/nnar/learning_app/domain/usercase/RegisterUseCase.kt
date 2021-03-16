package nnar.learning_app.domain.usercase

import nnar.learning_app.data.repository.UserRepository

class RegisterUseCase (private val userRepository: UserRepository){

    fun registerUser(username: String, email: String, password: String) = userRepository.registerUser(username, email, password)
    fun verifyUsername(username: String) = userRepository.verifyUsername(username)
    fun verifyEmail(email: String) = userRepository.verifyEmail(email)

}