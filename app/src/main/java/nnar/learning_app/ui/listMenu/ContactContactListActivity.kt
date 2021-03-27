package nnar.learning_app.ui.listMenu

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import nnar.learning_app.databinding.ActivityListMenuBinding
import nnar.learning_app.datainterface.ListMenuView

class ContactListActivity: AppCompatActivity(), ListMenuView {

    private lateinit var binding: ActivityListMenuBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityListMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}

