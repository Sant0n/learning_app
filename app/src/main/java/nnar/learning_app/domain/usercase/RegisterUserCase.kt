package nnar.learning_app.domain.usercase

import nnar.learning_app.data.AppRepository

class RegisterUserCase (private val appRepository: AppRepository){

    fun registerUser(username: String, email: String, password: String){ appRepository.registerUser(username, email, password)}

}