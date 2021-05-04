package nnar.learning_app.data.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nnar.learning_app.R
import nnar.learning_app.domain.model.ContactFirestore
import java.lang.Exception

//import com.google.firebase.firestore.DocumentSnapshot

class ContactFirestoreRepository {

    private val TAG = "Learning App - Contact List:"

    private lateinit var userUIDRepository: String

    private val db = Firebase.firestore.collection("users")

    companion object{
        val contactList =  mutableListOf<ContactFirestore>()
    }

    private val contactSet: MutableSet<ContactFirestore> = mutableSetOf(
        ContactFirestore(1, "raul", "+34 11111111111", R.drawable.avataaars1, "raul@gmail.com"),
        ContactFirestore(2, "sigrit", "+34 2222222222", R.drawable.avataaars2, "sigrit@gmail.com"),
        ContactFirestore(3, "nathan", "+34 3333333333", R.drawable.avataaars3, "nathan@gmail.com"),
        ContactFirestore(4, "alberto", "+34 4444444444", R.drawable.avataaars4, "alberto@gmail.com"),
        ContactFirestore(5, "nicole", "+34 5555555555", R.drawable.avataaars5, "nicole@gmail.com"),
        ContactFirestore(6, "agus", "+34 666666666", R.drawable.avataaars6, "agus@gmail.com")
    )

    fun createContact(name:String, phone:String, image: Int?, email:String): ContactFirestore{
        val newId = contactList.last().id + 1
        val auxImage = image ?: R.drawable.avataaars_default // If image is null then default
        return ContactFirestore(newId, name, phone, auxImage, email)
    }

    suspend fun writeDataOnFirestoreFirtsTime(userUID: String) {
        userUIDRepository = userUID
        for (contact in contactSet){
            try{
                val doc = db.document(userUID).collection("contacts").document(contact.id.toString())
                if(!doc.get().await().exists()){
                    writeDataOnFirestore(contact)
                    Log.d(TAG, "Contact ${contact.id} added")
                }else{
                    Log.d(TAG, "Contact ${contact.id} already exists")
                }
            }catch (e : Exception){
                Log.d(TAG, "Error writing contact ${contact.id}")
            }
        }
    }

    suspend fun writeDataOnFirestore(contact: ContactFirestore):Boolean{
        return try{
            db.document(userUIDRepository).collection("contacts")
                .document(contact.id.toString())
                .set(contact)
                .await()
            contactList.add(contact)
            true
        }catch (e : Exception){
            false
        }
    }

    suspend fun readContactsFirestore(): Boolean{
        return try{
            val auxList = mutableListOf<ContactFirestore>()
            val data = db.document(userUIDRepository).collection("contacts")
                .get()
                .await()
            for (d in data){
                auxList.add(d.toObject())
            }
            contactList.addAll(auxList)
            true
        }catch (e: Exception){
           false
        }
    }

    suspend fun removeContactFirestore(contact: ContactFirestore): Boolean {
        return try{
            db.document(userUIDRepository).collection("contacts")
                .document(contact.id.toString())
                .delete()
                .await()

            contactList.remove(contact)
            true
        }catch(e: Exception){
            false
        }
    }

    fun getItemCount() = contactList.size

    fun getContact(position: Int) = contactList[position]

    fun deleteLocalData(){
        userUIDRepository = " "
        contactList.clear()
    }
}