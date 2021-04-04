package nnar.learning_app.dataInterface

import nnar.learning_app.domain.model.Mobile

interface HomeView {

    fun showList(mobileList: List<Mobile>)
}