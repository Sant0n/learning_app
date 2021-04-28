package nnar.learning_app.userInterface.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.data.repository.MobileRepository
import nnar.learning_app.dataInterface.HomeView
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.domain.model.Mobile
import nnar.learning_app.domain.usecase.HomeUserUsecase


class HomeActivity: AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var userMobilesAdapter: UserMobilesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HomePresenter(this, HomeUserUsecase(MobileRepository()))

        binding.listOfMobiles.layoutManager = LinearLayoutManager(this)
        userMobilesAdapter = UserMobilesAdapter(presenter)
        binding.listOfMobiles.adapter = userMobilesAdapter

        val user = intent.getParcelableExtra<FirebaseUser>("user")!!
        presenter.getMobileList(user.uid)

        setListeners()

    }

    override fun updateData() {
        userMobilesAdapter.updateData()
    }

    override fun hideRemoveButton() {
        binding.removeButton.visibility = View.GONE
    }

    override fun showRemoveButton() {
        binding.removeButton.visibility = View.VISIBLE
    }


    private fun setListeners(){
        binding.addMobileButton.setOnClickListener {
            presenter.addMobile(Mobile("","Iphone","1.0.0",false))
        }

        binding.removeButton.setOnClickListener {
            presenter.removeSelected()
        }
    }




}