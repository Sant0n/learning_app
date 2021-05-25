package nnar.learning_app.ui.contactDetail

import android.content.Context
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import nnar.learning_app.datainterface.ContactDetailView
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase
import nnar.learning_app.utils.GlideApp

class ContactDetailPresenter(private val view: ContactDetailView, private val useCase: ContactFirestoreUseCase) {

    private val storageRef = Firebase.storage.reference

    fun loadImage(url:String, resource: ImageView, context: Context){
        GlideApp.with(context)
            .load(storageRef.child(url))
            .into(resource)
    }
}