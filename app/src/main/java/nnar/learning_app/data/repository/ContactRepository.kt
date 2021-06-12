package nnar.learning_app.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import nnar.learning_app.domain.model.Contact

/**
 * The repository to manage the data in Firestore.
 * @constructor It receives the [uid] of the current Google user
 */
class ContactRepository(private val uid: String) {

    // Internal contact info
    companion object var dataset = ArrayList<Pair<String, Contact>>()

    // Firestore connection
    private val database = Firebase.firestore
    private val contacts = database.collection(uid)
    private val storage = Firebase.storage.reference.child("pictures")

    /**
     * Returns the size of the list
     */
    fun size() = dataset.size

    /**
     * Resets the internal user IDs
     */
    fun reset() = dataset.clear()

    /**
     * Deletes contact on the given [position].
     */
    suspend fun removeContact(position: Int) = withContext(Dispatchers.IO) {
        // Delete contact in Firestore
        contacts.document(dataset[position].first).delete().addOnSuccessListener {
            Log.d("DELETE", "Contact removed")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to remove contact: " + it.localizedMessage)
        }

        // Delete image URI
        val pic = dataset[position].second.pic
        val imageReference = Firebase.storage.getReferenceFromUrl(pic)
        imageReference.delete().addOnSuccessListener {
            Log.d("DELETE", "Picture removed")
        }.addOnFailureListener {
            Log.d("ERROR", "Failed to remove picture: " + it.localizedMessage)
        }

        // Update local list
        dataset.removeAt(position)
    }

    /**
     * Gets the contact name based on the [position].
     */
    fun getContact(position: Int) = dataset[position].second

    /**
     * Gets the image URI from the contact given by the [position].
     */
    fun getImageURI(position: Int?) = if (position != null) dataset[position].second.pic else ""

    /**
     * Adds or updates a the given [contact]. [position] is used when a contact is updated.
     */
    suspend fun write(contact: Contact, position: Int? = null) = withContext(Dispatchers.IO) {
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

    /**
     * Uploads a picture given by the [uri] and assigns to it the given [contact].
     * If the contact already exists, a [position] is given.
     */
    suspend fun uploadPicture(
        uri: Uri,
        contact: Contact,
        position: Int? = null
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            // Store the selected picture
            val img = storage.child("${uid}_${contact.name}")
            img.putFile(uri).await()

            // Get the URL for the new image
            contact.pic = img.downloadUrl.await().toString()
            write(contact, position)

            // Image loaded
            Log.d("OK", "Image loaded: $contact.pic")
            true
        } catch (e: Exception) {
            Log.d("ERROR", "Failed to load image: " + e.localizedMessage)
            false
        }
    }

    /**
     * Get the current set of contacts
     */
    suspend fun getCurrentContactsId(): Boolean = withContext(Dispatchers.IO) {
        try {
            // Get each contact ID
            val docs = contacts.get().await()
            for (doc in docs) {
                // Document values
                val id = doc.id
                val name = doc.data["name"].toString()
                val state = doc.data["online"] as Boolean
                val pic = doc.data["pic"]?.toString() ?: ""

                // Object to store
                val contact = Contact(name, state, pic)
                dataset.add(Pair(id, contact))
            }
            sort()

            // Image loaded
            Log.d("OK", "Contacts loaded")
            true
        } catch (e: Exception) {
            Log.d("ERROR", "Failed to get contacts: " + e.localizedMessage)
            false
        }
    }

    /**
     * Sorts the dataset by contact name
     */
    private fun sort() = dataset.sortBy { it.second.name }
}