package nnar.learning_app.userInterface.home

import android.graphics.Bitmap
import android.net.Uri
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
    private var defaultImage = true

    internal fun getMobileList(userID: String) {
        viewModelScope.launch {
            val response = homeUserUsecase.getAllMobiles(userID)
            if (!response.error) homeView.updateData() else println("ERROR GETTING MOBILES: ${response.msg}")
        }
    }

    internal fun addMobile(name: String, version: String, image: Bitmap) {
        viewModelScope.launch {
            if (checkFields(name, version)) {
                homeView.clearFieldsError()
                val imgDir = if (!defaultImage) "mobiles/$name $version".trim().replace(" ", "_")
                else "mobiles/default.png"

                val response = homeUserUsecase.addMobile(
                    Mobile(
                        name = name,
                        version = version,
                        img_url = imgDir
                    ), image
                )
                defaultImage = true
                if (!response.error) {
                    homeView.dismissDialog()
                    homeView.updateData()
                } else println("ERROR ADDING MOBILE: ${response.msg}")
            }
        }
    }

    internal fun removeSelected() {
        viewModelScope.launch {
            itemsToRemove.sortDescending()
            for (item in itemsToRemove) {
                val mobile: Mobile = homeUserUsecase.getMobileAtPosition(item)
                //val response = homeUserUsecase.removeMobile(mobile)
                //if (!response.error) homeUserUsecase.removeLocally(item) else println("ERROR REMOVING ITEM AT POSITION $item")
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

    internal fun onPermissionResult(granted: Boolean) {
        if (granted) homeView.openGallery()
    }

    internal fun checkImagePicked(uri: Uri?) {
        defaultImage = if (uri != null) {
            homeView.changeDialogImage(uri)
            false
        } else true
    }

    private fun checkFields(name: String, version: String): Boolean {
        var response = true
        if (name.isEmpty()) {
            homeView.showNameNotFilled()
            response = false
        }
        if (version.isEmpty()) {
            homeView.showVersionNotFilled()
            response = false
        }
        return response
    }


}
