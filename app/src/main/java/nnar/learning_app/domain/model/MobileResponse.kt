package nnar.learning_app.domain.model

import com.google.firebase.storage.StorageReference

data class MobileResponse(val img_url: StorageReference, val name: String, val version: String, val favorite: Boolean) {
}