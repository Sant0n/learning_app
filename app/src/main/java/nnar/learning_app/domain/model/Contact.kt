package nnar.learning_app.domain.model

import androidx.annotation.DrawableRes

data class Contact(val id: Int, val name: String, val phoneNumber: String, @DrawableRes val image: Int, val email:String)
