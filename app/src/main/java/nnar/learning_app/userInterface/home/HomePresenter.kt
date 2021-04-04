package nnar.learning_app.userInterface.home

import nnar.learning_app.dataInterface.HomeView
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.usecase.HomeUserUsecase

class HomePresenter(private val homeView: HomeView, private val homeUserUsecase: HomeUserUsecase) {

    internal fun getMobileList(){
        homeView.showList(homeUserUsecase.getAllMobiles())
    }

    internal fun addMobile(mobile: Mobile){
        homeUserUsecase.addMobile(mobile)
    }

}
