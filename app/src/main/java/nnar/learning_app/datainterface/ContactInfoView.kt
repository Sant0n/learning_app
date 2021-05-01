package nnar.learning_app.datainterface

import android.content.Context
import android.widget.ImageView

interface ContactInfoView {
    fun getCurrentUserUID(): String
    fun getContactPic(): ImageView
}