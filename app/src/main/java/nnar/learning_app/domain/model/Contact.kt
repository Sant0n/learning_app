package nnar.learning_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The contact object to use throughout the app. It receives a [name], a [isOnline] flag and the
 * [pic] URI to define the contact.
 * @constructor Creates a constructor with the default values for the attributes.
 */
@Parcelize
data class Contact(
    var name: String = "",
    var isOnline: Boolean = false,
    var pic: String = ""
) : Parcelable