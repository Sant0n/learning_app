package nnar.learning_app.domain.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactFirestore (val id: Int = 0,
                        val name: String = "",
                        val phoneNumber: String = "",
                        @DrawableRes val image: Int = 0,
                        val email:String = ""
                        ) : Parcelable