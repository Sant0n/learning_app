package nnar.learning_app.dataInterface

import nnar.learning_app.domain.model.Mobile

interface MobileRowView {
    fun render(mobile: Mobile){}
    fun markAsSelected(){}
    fun markAsUnSelected(){}
    fun markAsFavorite(){}
    fun removeAsFavorite(){}
}