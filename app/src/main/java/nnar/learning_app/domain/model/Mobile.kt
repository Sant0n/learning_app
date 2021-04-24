package nnar.learning_app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mobile(val img_url: String = "", val name: String = "", val version: String = "", val favorite: Boolean = false): Parcelable
