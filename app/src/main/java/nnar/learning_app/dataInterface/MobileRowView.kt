package nnar.learning_app.dataInterface

import nnar.learning_app.domain.model.MobileResponse

interface MobileRowView {
    fun render(mobile: MobileResponse){}
    fun markAsSelected(){}
    fun markAsUnSelected(){}
    fun markAsFavorite(){}
    fun removeAsFavorite(){}
}