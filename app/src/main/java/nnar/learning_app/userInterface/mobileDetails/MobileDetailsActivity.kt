package nnar.learning_app.userInterface.mobileDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.databinding.ActivityMobileDetailsBinding
import nnar.learning_app.domain.model.Mobile


class MobileDetailsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMobileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mobile = intent.getParcelableExtra<Mobile>("mobile")!!
        binding.mobileDetailsName.text = binding.mobileDetailsName.text.toString() + mobile.name
        binding.mobileDetailsVersion.text = binding.mobileDetailsVersion.text.toString() + mobile.version

    }

}