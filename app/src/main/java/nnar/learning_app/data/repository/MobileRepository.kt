package nnar.learning_app.data.repository

import nnar.learning_app.domain.model.Mobile

class MobileRepository {

    private val listOfUsers: MutableList<Mobile> = arrayListOf(
        Mobile("", "Samsung S10", "1.0.2",false),
        Mobile("", "Samsung S9", "1.5.0",false),
        Mobile("", "Meizu", "1.3.7",true),
        Mobile("", "Xiaomi M10", "valdilecha",false),
        Mobile("", "Xiaomi M9", "valdilecha",false)
    )

    /** This should receive an user*/
    fun getAllMobiles(): List<Mobile>{
        return listOfUsers
    }

    fun addMobile(mobile: Mobile){
        listOfUsers.add(mobile)
    }
}