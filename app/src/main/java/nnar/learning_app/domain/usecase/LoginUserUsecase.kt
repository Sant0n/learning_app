package nnar.learning_app.domain.usecase

import nnar.learning_app.data.repository.UserRepository

class LoginUserUsecase(private val userRepository: UserRepository) {

    fun loginUser(email: String, password: String) = userRepository.loginUser(email, password)
}