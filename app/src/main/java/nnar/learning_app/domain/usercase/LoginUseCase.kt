package nnar.learning_app.domain.usercase

import nnar.learning_app.data.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {

    fun loginUser(username: String, password: String) = userRepository.loginUser(username, password)

}