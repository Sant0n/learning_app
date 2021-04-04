package nnar.learning_app.userInterface.home

import nnar.learning_app.dataInterface.MobileRowView
import nnar.learning_app.domain.model.Mobile

class MobileListPresenter {

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

    internal fun getMobile(position: Int) = listOfMobiles[position]

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
            listOfMobiles.removeAt(item)
        }
        itemsToRemove.clear()
    }


}