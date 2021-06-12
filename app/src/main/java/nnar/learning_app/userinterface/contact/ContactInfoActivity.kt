package nnar.learning_app.userinterface.contact

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import nnar.learning_app.databinding.ActivityContactBinding
import nnar.learning_app.datainterface.ContactInfoView
import nnar.learning_app.domain.model.Contact

/**
 * The view to see the contact details.
 * @constructor Creates an empty constructor.
 */
class ContactInfoActivity : AppCompatActivity(), ContactInfoView {
    // Activity binding
    private lateinit var uid: String
    private lateinit var binding: ActivityContactBinding
    private lateinit var presenter: ContactInfoPresenter

    /**
     * Get user UID
     */
    override fun getCurrentUserUID() = uid

    /**
     * Creates the activity, sets the presenter and the listeners.
     * [savedInstanceState] contains the current state of the app
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set activity binding
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get contact info
        val contact = intent.getParcelableExtra<Contact>("contact")!!
        val name: String = contact.name
        val state: Boolean = contact.isOnline

        // Get user info
        uid = intent.getStringExtra("uid")!!

        // Set presenter
        presenter = ContactInfoPresenter()

        // Set contact info
        binding.contactName.text = name
        binding.stateInfo.isEnabled = state
        binding.stateInfo.text = presenter.getStateFullName(state)

        // Set contact picture
        Picasso.get()
            .load(intent.getParcelableExtra<Uri>("uri")!!)
            .resize(1000, 1000)
            .centerCrop()
            .into(binding.contactPic)
    }

    /**
     * Destroys the activity
     */
    override fun onDestroy() {
        super.onDestroy()
        finishAfterTransition()
    }
}