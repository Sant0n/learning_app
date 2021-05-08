package nnar.learning_app.datainterface

import android.content.Context

interface HomeView {
    fun updateAdapter()
    fun getCurrentUserUID(): String
    fun getContext(): Context
}