package nnar.learning_app.userInterface.home

import nnar.learning_app.dataInterface.HomeView
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.usecase.HomeUserUsecase
import kotlin.concurrent.thread

class HomePresenter(private val homeView: HomeView, private val homeUserUsecase: HomeUserUsecase) {

    internal fun getMobileList(){
        thread {
            val list = homeUserUsecase.getAllMobiles()
            list.get().addOnSuccessListener { result ->
                for (document in result){
                    homeView.showList(document.toObject(Mobile::class.java))
                }
            }.addOnFailureListener { e ->
                println("Error: $e")
            }
        }
    }

    internal fun addMobile(mobile: Mobile){
        val newMobile = homeUserUsecase.addMobile()
        newMobile.set(mobile).addOnSuccessListener { _ ->
            homeView.showList(mobile)
        }.addOnFailureListener { e->
            println("Error: $e")
        }
    }


}
