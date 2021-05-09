package nnar.learning_app.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import nnar.learning_app.domain.model.Contact

@ExperimentalCoroutinesApi
class ContactRepository(uid: String) {

    // Internal contact info
    companion object {
        private val pictures = ArrayList<String>()
        private var dataset = ArrayList<Pair<String, Contact>>()
    }

    // Firestore connection
    private val database = Firebase.firestore
    private val contacts = database.collection(uid)
    private val storage = Firebase.storage.reference.child("pictures")

    // Size of the list
    fun size() = dataset.size

    // Reset internal IDs
    fun reset() {
        pictures.clear()
        dataset.clear()
    }

    // Delete contact
    fun removeContact(position: Int) {
        // Delete contact in Firestore
        contacts.document(dataset[position].first).delete().addOnSuccessListener {
            Log.d("DELETE", "Contact removed")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
        }

        // Update local list
        dataset.removeAt(position)
    }

    // Get contact name
    fun getContact(position: Int) = read(position)

    // Add/Update data
    fun write(contact: Contact, position: Int? = null) {
        // Define task
        val doc: DocumentReference
        if (position == null) {
            doc = contacts.document()
            dataset.add(Pair(doc.id, contact))
        } else {
            doc = contacts.document(dataset[position].first)
            dataset[position] = dataset[position].copy(second = contact)
        }
        sort()

        // Create/Update contact
        doc.set(contact).addOnSuccessListener {
            Log.d("OK", "Contact loaded")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to load: " + it.localizedMessage)
        }
    }

    // Get random or specific image
    fun getImageURI(position: Int? = null): String {
        return if (position != null) {
            dataset[position].second.pic
        } else {
            pictures[(0 until pictures.size).random()]
        }
    }

    // Get current contacts
    suspend fun getCurrentContactsId(): Boolean = suspendCancellableCoroutine { cont ->
        // Get each contact ID
        contacts.get().addOnSuccessListener { docs ->
            for (doc in docs) {
                // Document values
                val id = doc.id
                val name = doc.data["name"].toString()
                val state = doc.data["online"] as Boolean

                // Object to store
                val pic = doc.data["pic"]?.toString() ?: getImageURI()
                val contact = Contact(name, state, pic)
                dataset.add(Pair(id, contact))

                // Check if contact should be updated
                if (!doc.data.containsKey("pic"))
                    write(contact, dataset.size - 1)
            }
            sort()
            cont.resume(true, null)
        }.addOnFailureListener {
            cont.resume(false, null)
        }
    }

    // Get images
    suspend fun loadImages(): Boolean = suspendCancellableCoroutine { cont ->
        storage.listAll().addOnSuccessListener { files ->
            for (file in files.items) {
                file.downloadUrl.addOnSuccessListener { uri ->
                    pictures.add(uri.toString())
                    Log.d("OK", "Image loaded: $uri")
                }.addOnFailureListener {
                    Log.d("ERROR", "Failed to get image: " + it.localizedMessage)
                }
            }
            cont.resume(true, null)
        }.addOnFailureListener {
            cont.resume(false, null)
        }
    }

    // Sort dataset by contact name
    private fun sort() = dataset.sortBy { it.second.name }

    // Read contact info
    private fun read(position: Int) = dataset[position].second
}