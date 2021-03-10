package nnar.learning_app.domain.usercase

import nnar.learning_app.data.AppRepository

class LoginUserCase(private val appRepository: AppRepository) {

    fun verifyUser(username: String, password: String) { appRepository.verifyUser(username, password) }

}