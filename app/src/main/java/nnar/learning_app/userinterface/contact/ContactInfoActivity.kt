package nnar.learning_app.userinterface.contact

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nnar.learning_app.databinding.ActivityContactBinding
import nnar.learning_app.datainterface.ContactInfoView
import nnar.learning_app.domain.model.Contact

@ExperimentalCoroutinesApi
class ContactInfoActivity : AppCompatActivity(), ContactInfoView {
    // Activity binding
    private lateinit var uid: String
    private lateinit var binding: ActivityContactBinding
    private lateinit var presenter: ContactInfoPresenter

    // Get user UID
    override fun getCurrentUserUID() = uid

    // Get current context
    override fun getContext(): Context = binding.root.context

    // Get the contact picture
    override fun getContactPic() = binding.contactPic

    // Create activity
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
        presenter = ContactInfoPresenter(this)

        // Set contact info
        binding.contactName.text = name
        binding.stateInfo.isEnabled = state
        binding.stateInfo.text = presenter.getStateFullName(state)
        presenter.setImage()
    }
}