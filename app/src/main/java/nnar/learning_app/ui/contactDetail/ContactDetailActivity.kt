package nnar.learning_app.ui.contactDetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.databinding.ActivityContactDetailBinding
import nnar.learning_app.domain.model.Contact

class ContactDetailActivity: AppCompatActivity() {

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

        val contact = intent.getParcelableExtra<Contact>("contact")!!

        contactImage.setImageResource(contact.image)
        contactName.text = contact.name
        contactPhone.text = contact.phoneNumber
        contactEmail.text = contact.email

    }

}