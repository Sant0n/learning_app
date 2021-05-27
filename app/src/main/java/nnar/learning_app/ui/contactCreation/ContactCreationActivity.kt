package nnar.learning_app.ui.contactCreation

import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityContactCreationBinding
import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactUseCase

class ContactCreationActivity: AppCompatActivity(), ContactCreationView {

    private lateinit var binding: ActivityContactCreationBinding
    private lateinit var presenter: ContactCreationPresenter
    private lateinit var button: Button
    private lateinit var contactImage: ImageView
    private lateinit var contactName:TextView
    private lateinit var contactEmail:TextView
    private lateinit var contactPhone:TextView

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        presenter.permissionResult(granted)
    }

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        contactImage.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactCreationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        button = binding.contactCreationButton
        contactImage = binding.contactCreationImage
        contactName = binding.contactCreationEditTextName
        contactEmail = binding.contactCreationEditTextEmail
        contactPhone = binding.contactCreationEditTextPhone

        presenter = ContactCreationPresenter(this, ContactUseCase(ContactRepository()))

        setListeners()
    }

    private fun setListeners(){
        contactImage.setOnClickListener {
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        button.setOnClickListener {
            presenter.saveContact()
        }
    }

    override fun permissionResult(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun openGallery() {
        Toast.makeText(this, "llegue", Toast.LENGTH_SHORT).show()
        selectImage.launch("image/*")
    }
}