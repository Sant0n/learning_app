package nnar.learning_app.domain.usercase

import nnar.learning_app.data.repository.UserRepository

class RegisterUserCase (private val userRepository: UserRepository){

    fun registerUser(username: String, email: String, password: String) = userRepository.registerUser(username, email, password)

}