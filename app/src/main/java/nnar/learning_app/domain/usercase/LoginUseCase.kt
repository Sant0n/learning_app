package nnar.learning_app.domain.usercase

import nnar.learning_app.data.repository.UserRepository

class LoginUserCase(private val userRepository: UserRepository) {

    fun verifyUser(username: String, password: String) { userRepository.verifyUser(username, password) }

}