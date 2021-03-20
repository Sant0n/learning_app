package nnar.learning_app.userInterface.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
//import nnar.learning_app.databinding.ActivityHomeBinding


class HomeActivity: AppCompatActivity() {

    //private lateinit var binding: ActivityHomeBinding
    //private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityHomeBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        //binding.listOfMobiles.layoutManager = LinearLayoutManager(this)
        //userMobilesAdapter = UserMobilesAdapter(presenter)
        //binding.listOfMobiles.adapter = userMobilesAdapter

        //presenter = HomePresenter(this, LoginUserUsecase(UserRepository()))
        setListeners()

    }

    private fun setListeners() {}

}