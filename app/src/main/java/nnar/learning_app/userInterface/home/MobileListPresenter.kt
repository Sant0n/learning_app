package nnar.learning_app.userInterface.home

import nnar.learning_app.data.repository.MobileRepository
import nnar.learning_app.dataInterface.MobileRowView
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.usecase.HomeUserUsecase

class MobileListPresenter {

    private val rowListUseCase = HomeUserUsecase(MobileRepository())
    private var listOfMobiles: MutableList<Mobile> = mutableListOf()
    private var itemsToRemove: MutableList<Int> = mutableListOf()

    internal fun updateData(mList: List<Mobile>) {
        listOfMobiles.clear()
        listOfMobiles = mList as MutableList<Mobile>
    }

    internal fun getItemCount(): Int = listOfMobiles.size

    internal fun onBindMobileRowViewAtPosition(position: Int, rowView: MobileRowView) {
        val mobile = listOfMobiles[position]
        rowView.render(mobile)
    }

    internal fun getMobile(position: Int): Mobile = listOfMobiles[position]

    internal fun addMobile(mobile: Mobile){
        listOfMobiles.add(mobile)
    }

    internal fun checkLongSelected(pos: Int, rowView: MobileRowView): Boolean{
        if(pos in itemsToRemove){
            itemsToRemove.remove(pos)
            rowView.markAsUnSelected()
        } else {
            itemsToRemove.add(pos)
            rowView.markAsSelected()
        }
        return itemsToRemove.isEmpty()
    }

    internal fun removeItems(){
        itemsToRemove.sortDescending()
        for (item in itemsToRemove){
            val removed = rowListUseCase.removeMobile(listOfMobiles[item])
            removed.addOnSuccessListener { documents ->
                for (document in documents){
                    document.reference.delete()
                }
                listOfMobiles.removeAt(item)
            }.addOnFailureListener { e->
                println("Error: $e")
            }


        }
        itemsToRemove.clear()
    }


}