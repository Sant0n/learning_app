package nnar.learning_app.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import nnar.learning_app.domain.model.Mobile

class MobileRepository {

    private val db = FirebaseFirestore.getInstance()

    private val listOfUsers: MutableList<Mobile> = arrayListOf(
        Mobile("", "Samsung S10", "1.0.2",false),
        Mobile("", "Samsung S9", "1.5.0",false),
        Mobile("", "Meizu", "1.3.7",true),
        Mobile("", "Xiaomi M10", "valdilecha",false),
        Mobile("", "Xiaomi M9", "valdilecha",false)
    )

    fun getAllMobiles(): List<Mobile>{
        var mobileList: MutableList<Mobile> = mutableListOf()

        val mobileRef = db.collection("Users").document("OhuLwXFbDpIP1wHi2zWe").collection("mobiles")

        mobileRef.get().addOnSuccessListener { result ->
           for (document in result){
               val realMobileRef = db.document(document.data["reference"] as String)
               realMobileRef.get().addOnSuccessListener { subResult ->
                   println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + subResult.data!!.values)
                   val mobile = subResult.toObject(Mobile::class.java)
                   mobileList.add(mobile!!)
               }
                   .addOnFailureListener {
                       println("ESTO HA CASCADO COSA FINAAAAAAAAAAAAAAAAAAAAAA")
                   }
           }
        }
            .addOnFailureListener {
                println("------------------------ Algo ha cascado u.u ")
            }


        return mobileList
    }

    /** This should receive an user
    fun getAllMobiles(): List<Mobile>{
        return listOfUsers
    }*/

    fun addMobile(mobile: Mobile){
        listOfUsers.add(mobile)
    }
}