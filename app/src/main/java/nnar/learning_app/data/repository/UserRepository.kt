package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.User
import nnar.learning_app.domain.model.UserResponse

class UserRepository {

    private val listOfUsers: MutableList<User> = arrayListOf(
        User("93agusmartin@gmail.com", "agus69", "pollote"),
        User("nicolegp94@gmail.com", "nicky", "chocho"),
        User("nathan.crengifo@gmail.com", "nathaniel", "pervertido"),
        User("elderaul@gmail.com", "raul", "valdilecha")
    )

    fun registerUser(newUser: User): UserResponse {
        /**
         * Asumo que el usuario tambien tiene la misma contraseña para hacerlo corto, cuando tengamos BD esto ya no hara falta asi que
         * no voy a perder mucho tiempo en recorrer la lista usuario por usuario comprobando solo el email.
         */
        return if (newUser !in listOfUsers) {
            println(listOfUsers)
            listOfUsers += newUser
            println(listOfUsers)
            println("-----------------------------------")
            UserResponse(false, "Register successful")
        } else {
            println("Entre aqui")
            UserResponse(true, "This user is already registered")
        }

    }

    fun loginUser(userId: String, pass: String): UserResponse {
        for (user in listOfUsers) {
            if ((user.email == userId && user.password == pass) || (user.username == userId && user.password == pass)) {
               return  UserResponse(false, "Login successful")
            }
        }
        return UserResponse(true, "Credentials not valid")
    }
}