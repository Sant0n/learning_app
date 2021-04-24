package nnar.learning_app.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellableContinuation
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

        // Size of the list
        fun size() = ids.size

        // Delete contact
        fun removeContact(position: Int) {
            // Delete contact in Firestore
            contacts.document(ids[position].toString()).delete().addOnSuccessListener {
                Log.d("DELETE", "Contact removed")
            }.addOnFailureListener {
                Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
            }

            // Update local list
            ids.removeAt(position)
        }

        // Add/Update data
        fun write(
            contact: Contact,
            position: Int? = null,
            cont: CancellableContinuation<Boolean>? = null
        ) {
            // Set Contact ID
            val id = if (position == null) lastContactId else ids[position]

            // Create/Update contact
            contacts.document(id.toString()).set(contact).addOnSuccessListener {
                if (position == null) ids.add(id)
                cont?.resume(true, null)
                Log.d("OK", "Contact loaded")
            }.addOnFailureListener {
                cont?.resume(false, null)
                Log.d("ERROR", "Failed to load: " + it.localizedMessage)
            }
        }

        // Get contact name
        suspend fun getContactName(position: Int) = read(ids[position]).name

        // Get state info
        suspend fun getContactState(position: Int): Boolean = read(ids[position]).isOnline

        // Get state text
        suspend fun getStateText(position: Int) = if (read(ids[position]).isOnline) "I" else "O"

        // Add new contact
        suspend fun addContact(): Boolean = suspendCancellableCoroutine { cont ->
            write(createContact(), cont = cont)
        }

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
                // Get each contact ID
                if (ids.size == 0)
                    for (doc in docs)
                        ids.add(doc.id.toInt())

                // Set max ID
                lastContactId = ids.maxOrNull() ?: 0

                // Resume success
                cont.resume(true, null)
            }.addOnFailureListener {
                cont.resume(false, null)
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
    }
}