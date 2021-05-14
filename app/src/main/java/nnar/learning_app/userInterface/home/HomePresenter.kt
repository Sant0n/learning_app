package nnar.learning_app.userInterface.home

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nnar.learning_app.dataInterface.HomeView
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.usecase.HomeUserUsecase


class HomePresenter(private val homeView: HomeView, private val homeUserUsecase: HomeUserUsecase) :
    ViewModel() {

    private val itemsToRemove = mutableListOf<Int>()

    internal fun getMobileList(userID: String) {
        viewModelScope.launch {
            val response = homeUserUsecase.getAllMobiles(userID)
            if (!response.error) homeView.updateData() else println("ERROR GETTING MOBILES: ${response.msg}")
        }
    }

    internal fun addMobile(mobile: Mobile) {
        viewModelScope.launch {
            val response = homeUserUsecase.addMobile(mobile)
            if (!response.error) homeView.updateData() else println("ERROR ADDING MOBILE: ${response.msg}")
        }
    }

    internal fun removeSelected() {
        viewModelScope.launch {
            itemsToRemove.sortDescending()
            for (item in itemsToRemove) {
                val mb = homeUserUsecase.getMobileAtPosition(item)
                val mobile = Mobile(mb.img_url.path, mb.name, mb.version, mb.favorite)
                val response = homeUserUsecase.removeMobile(mobile)
                if (!response.error) homeUserUsecase.removeLocally(item) else println("ERROR REMOVING ITEM AT POSITION $item")
            }
            homeView.updateData()
            itemsToRemove.clear()
            homeView.hideRemoveButton()
        }
    }

    internal fun getMobileAtPosition(position: Int) = homeUserUsecase.getMobileAtPosition(position)

    internal fun getItemCount() = homeUserUsecase.getItemCount()

    internal fun checkOnLongSelected(position: Int, holder: UserMobileRowView) {
        if (position in itemsToRemove) {
            itemsToRemove.remove(position)
            holder.markAsUnSelected()
        } else {
            itemsToRemove.add(position)
            holder.markAsSelected()
        }
        if (itemsToRemove.isEmpty()) homeView.hideRemoveButton() else homeView.showRemoveButton()
    }

    internal fun searchMobile(event: Int) {
        if (event == EditorInfo.IME_ACTION_DONE) homeView.clearSearchFocus()
    }

    internal fun checkKeyboardStatus(visible: Boolean) {
        if (visible) homeView.hideAddButton() else homeView.showAddButton()
    }


}
