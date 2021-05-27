package nnar.learning_app.domain.usecase

import android.graphics.Bitmap
import nnar.learning_app.data.repository.MobileRepository
import nnar.learning_app.domain.model.Mobile

class HomeUserUsecase(private val mobileRepository: MobileRepository) {

    suspend fun getAllMobiles(userID: String) = mobileRepository.getAllMobiles(userID)
    suspend fun addMobile(mobile: Mobile, image: Bitmap) = mobileRepository.addMobile(mobile, image)
    suspend fun removeMobile(mobile: Mobile, image: Bitmap) = mobileRepository.removeMobile(mobile, image)
    fun getMobileAtPosition(pos: Int) = mobileRepository.getMobileAtPosition(pos)
    fun getItemCount() = mobileRepository.getItemCount()
    fun removeLocally(pos: Int) = mobileRepository.removeLocally(pos)
}