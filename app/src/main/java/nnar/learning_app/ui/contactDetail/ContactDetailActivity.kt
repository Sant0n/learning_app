package nnar.learning_app.ui.contactDetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.data.repository.ContactFirestoreRepository
import nnar.learning_app.databinding.ActivityContactDetailBinding
import nnar.learning_app.datainterface.ContactDetailView
import nnar.learning_app.domain.model.ContactFirestore
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase
import nnar.learning_app.ui.contactListMenu.ContactListPresenter

class ContactDetailActivity: AppCompatActivity(), ContactDetailView {


    private lateinit var presenter: ContactDetailPresenter
    private lateinit var binding: ActivityContactDetailBinding


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val contactName: TextView = binding.contactDetailNameText
        val contactImage: ImageView = binding.contactDetailImage
        val contactPhone: TextView = binding.contactDetailPhoneText
        val contactEmail: TextView  = binding.contactDetailEmailText

        val contact = intent.getParcelableExtra<ContactFirestore>("contact")!!

        presenter = ContactDetailPresenter(this, ContactFirestoreUseCase(
            ContactFirestoreRepository()))

        presenter.loadImage(contact.image, contactImage, this)
        contactName.text = contact.name
        contactPhone.text = contact.phoneNumber
        contactEmail.text = contact.email
    }

}