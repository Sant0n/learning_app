package nnar.learning_app.domain.usecase

import nnar.learning_app.data.repository.MobileRepository
import nnar.learning_app.domain.model.Mobile

class HomeUserUsecase(private val mobileRepository: MobileRepository) {

    suspend fun getAllMobiles(userID: String) = mobileRepository.getAllMobiles(userID)
    suspend fun addMobile(mobile: Mobile) = mobileRepository.addMobile(mobile)
    suspend fun removeMobile(mobile: Mobile) = mobileRepository.removeMobile(mobile)
    fun getMobileAtPosition(pos: Int) = mobileRepository.getMobileAtPosition(pos)
    fun getItemCount() = mobileRepository.getItemCount()
    fun removeLocally(pos: Int) = mobileRepository.removeLocally(pos)
}