package nnar.learning_app.data.repository

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import nnar.learning_app.domain.model.Contact

@ExperimentalCoroutinesApi
class FirebaseRepository(uid: String) : ViewModel() {

    // Internal contact ID
    companion object

    var ids = ArrayList<String>()

    // Firestore connection
    private val database = Firebase.firestore
    private val contacts = database.collection(uid)

    // Size of the list
    fun size() = ids.size

    // Reset internal IDs
    fun reset() = ids.clear()

    // Delete contact
    fun removeContact(position: Int) {
        // Delete contact in Firestore
        contacts.document(ids[position]).delete().addOnSuccessListener {
            Log.d("DELETE", "Contact removed")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
        }

        // Update local list
        ids.removeAt(position)
    }

    // Add newly created contact
    suspend fun addContact(contact: Contact): Boolean = suspendCancellableCoroutine { cont ->
        write(contact, cont = cont)
    }

    // Modify contact
    fun modifyContact(contact: Contact, position: Int) = write(contact, position)

    // Get contact name
    suspend fun getContact(position: Int) = read(position)

    // Change the state
    suspend fun changeState(position: Int): Contact {
        // Get current contact
        val contact = read(position)

        // Modify and update contact
        contact.isOnline = !contact.isOnline
        write(contact, position)

        // Return modified contact
        return contact
    }

    // Get current contacts
    suspend fun getCurrentContactsId(): Boolean = suspendCancellableCoroutine { cont ->
        contacts.get().addOnSuccessListener { docs ->
            // Get each contact ID
            for (doc in docs) ids.add(doc.id)

            // Resume success
            cont.resume(true, null)
        }.addOnFailureListener {
            cont.resume(false, null)
        }
    }

    // Read contact info
    private suspend fun read(position: Int): Contact {
        return contacts.document(ids[position]).get().await().toObject(Contact::class.java)!!
    }

    // Add/Update data
    private fun write(
        contact: Contact,
        position: Int? = null,
        cont: CancellableContinuation<Boolean>? = null
    ) {
        // Define task
        val task = if (position == null) contacts.document() else contacts.document(ids[position])

        // Create/Update contact
        task.set(contact).addOnSuccessListener {
            if (position == null) ids.add(task.id)
            cont?.resume(true, null)
            Log.d("OK", "Contact loaded")
        }.addOnFailureListener {
            cont?.resume(false, null)
            Log.d("ERROR", "Failed to load: " + it.localizedMessage)
        }
    }
}