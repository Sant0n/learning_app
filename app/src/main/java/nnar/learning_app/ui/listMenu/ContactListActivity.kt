package nnar.learning_app.ui.listMenu

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.databinding.ActivityContactListBinding
import nnar.learning_app.datainterface.ContactListView

class ContactListActivity: AppCompatActivity(), ContactListView {

    private lateinit var binding: ActivityContactListBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityContactListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}

