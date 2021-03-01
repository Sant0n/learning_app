package nnar.learning_app.domain.usecase

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.domain.model.User

class RegisterUserUsecase(private val userRepository: UserRepository) {

    fun registerUser(user: User) = userRepository.registerUser(user)
}