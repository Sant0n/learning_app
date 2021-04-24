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

    // Firestore connection
    private val database = Firebase.firestore
    private val contacts = database.collection(uid)

    // Size of the list
    fun size() = ContactIDRepository.ids.size

    // Delete contact
    fun removeContact(position: Int) {
        // Get contact ID
        val contactID = ContactIDRepository.ids[position].toString()

        // Delete contact in Firestore
        contacts.document(contactID).delete().addOnSuccessListener {
            Log.d("DELETE", "Contact removed")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
        }

        // Update local list
        ContactIDRepository.ids.removeAt(position)
    }

    // Add/Update data
    fun write(
        contact: Contact,
        position: Int? = null,
        cont: CancellableContinuation<Boolean>? = null
    ) {
        // Set Contact ID
        val id = if (position == null)
            ContactIDRepository.lastContactId
        else
            ContactIDRepository.ids[position]

        // Create/Update contact
        contacts.document(id.toString()).set(contact).addOnSuccessListener {
            // Add new contact ID on create
            if (position == null)
                ContactIDRepository.ids.add(id)
            cont?.resume(true, null)

            // Set success message
            Log.d("OK", "Contact loaded")
        }.addOnFailureListener {
            // Set failure message
            cont?.resume(false, null)
            Log.d("ERROR", "Failed to load: " + it.localizedMessage)
        }
    }

    // Get contact name
    suspend fun getContact(position: Int) = read(position)

    // Add new contact
    suspend fun addContact(): Boolean = suspendCancellableCoroutine { cont ->
        write(createContact(), cont = cont)
    }

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
            if (size() == 0)
                for (doc in docs)
                    ContactIDRepository.ids.add(doc.id.toInt())

            // Set max ID
            ContactIDRepository.lastContactId = ContactIDRepository.ids.maxOrNull() ?: 0

            // Resume success
            cont.resume(true, null)
        }.addOnFailureListener {
            cont.resume(false, null)
        }
    }

    // Read contact info
    private suspend fun read(position: Int): Contact {
        val id = ContactIDRepository.ids[position]
        return contacts.document(id.toString()).get().await().toObject(Contact::class.java)!!
    }

    // Create new contact
    private fun createContact(state: Boolean = true): Contact {
        val name = "Person " + ++ContactIDRepository.lastContactId
        return Contact(name, state)
    }

}