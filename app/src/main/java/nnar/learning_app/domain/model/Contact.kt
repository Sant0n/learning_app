package nnar.learning_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(var name: String, var isOnline: Boolean): Parcelable
