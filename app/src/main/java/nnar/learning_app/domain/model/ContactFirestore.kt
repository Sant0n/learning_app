package nnar.learning_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactFirestore (val id: Int = 0,
                        val name: String = "",
                        val phoneNumber: String = "",
                        val image: String = "",
                        val email:String = ""
                        ): Parcelable