package nnar.learning_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.annotation.DrawableRes

@Parcelize
data class  Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    @DrawableRes val image: Int,
    val email:String): Parcelable
