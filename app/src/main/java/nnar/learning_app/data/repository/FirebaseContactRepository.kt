package nnar.learning_app.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import nnar.learning_app.domain.model.Contact

class FirebaseContactRepository : ViewModel() {

    companion object {
        // Id counter
        private var lastContactId: Int = 0
        private var ids = ArrayList<Int>()

        // Firestore connection
        @SuppressLint("StaticFieldLeak")
        private val database = Firebase.firestore
        private val contacts = database.collection("contact")

        // Add/Update data
        fun write(contact: Contact, position: Int? = null) {
            val id = if (position == null) lastContactId else ids[position]
            contacts.document(id.toString()).set(contact).addOnSuccessListener {
                if (position == null) ids.add(id)
                Log.d("OK", "Contact loaded")
            }.addOnFailureListener {
                Log.d("ERROR", "Failed to load: " + it.localizedMessage)
            }
        }

        // Size of the list
        fun size() = ids.size

        // Add new contact
        fun addContact(state: Boolean = true) = write(createContact(state))

        // Get contact
        suspend fun getContact(position: Int) = read(ids[position])

        // Get contact name
        suspend fun getContactName(position: Int) = read(ids[position]).name

        // Get state info
        suspend fun getContactState(position: Int): Boolean = read(ids[position]).isOnline

        // Get state text
        suspend fun getStateText(position: Int) = if (read(ids[position]).isOnline) "I" else "O"

        // Change the state
        suspend fun changeState(position: Int): Boolean {
            val contact = read(ids[position])
            contact.isOnline = !contact.isOnline
            write(contact, position)
            return contact.isOnline
        }

        // Get current contacts
        suspend fun getCurrentContactsId(): Boolean = suspendCancellableCoroutine { cont ->
            contacts.get().addOnSuccessListener { docs ->
                for (doc in docs) ids.add(doc.id.toInt())
                lastContactId = ids.maxOrNull() ?: 0
                cont.resume(true, null)
            }
        }

        // Delete contact
        suspend fun removeContact(position: Int): Boolean = suspendCancellableCoroutine { cont ->
            contacts.document(ids[position].toString()).delete().addOnSuccessListener {
                ids.removeAt(position)
                cont.resume(true, null)
                Log.d("DELETE", "Contact removed")
            }.addOnFailureListener {
                cont.resume(false, null)
                Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
            }
        }

        // Read contact info
        private suspend fun read(id: Int): Contact {
            return contacts.document(id.toString()).get().await().toObject(Contact::class.java)!!
        }

        // Create new contact
        private fun createContact(state: Boolean = true): Contact {
            val name = "Person " + ++lastContactId
            return Contact(name, state)
        }

        // Create initial list of contacts
        private fun createContactsList(numContacts: Int) {
            for (i in 1..numContacts) write(createContact(i <= numContacts / 2))
        }
    }
}