package nnar.learning_app.datainterface

import android.content.Context
import android.net.Uri
import android.widget.ImageView

interface HomeView {
    fun updateAdapter()
    fun getCurrentUserUID(): String
    fun getContext(): Context
    fun getContactPic(): ImageView
    fun getGetUserPicture(): Uri
}