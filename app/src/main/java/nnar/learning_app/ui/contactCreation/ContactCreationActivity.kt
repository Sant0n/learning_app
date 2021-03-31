package nnar.learning_app.ui.contactCreation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.data.repository.ContactRepository
import nnar.learning_app.databinding.ActivityContactCreationBinding
import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactUseCase

class ContactCreationActivity: AppCompatActivity(), ContactCreationView {

    private lateinit var binding: ActivityContactCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactCreationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val presenter = ContactCreationPresenter(this, ContactUseCase(ContactRepository()))

        setListeners()
    }

    private fun setListeners(){
        
    }
}