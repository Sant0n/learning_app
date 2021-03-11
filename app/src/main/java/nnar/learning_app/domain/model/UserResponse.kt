package nnar.learning_app.domain.model

/*
1st parameter should be User type, but in order to simplify only the username is passed
 */
data class UserResponse(val username: String, val responseValue: Boolean = true, val msg:String)
