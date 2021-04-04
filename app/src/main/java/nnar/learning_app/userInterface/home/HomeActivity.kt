package nnar.learning_app.userInterface.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.listOfMobiles.layoutManager = LinearLayoutManager(this)
        userMobilesAdapter = UserMobilesAdapter(binding.removeButton)
        binding.listOfMobiles.adapter = userMobilesAdapter

        presenter = HomePresenter(this, HomeUserUsecase(MobileRepository()))
        presenter.getMobileList()
        
        setListeners()

    }

    override fun showList(mobileList: List<Mobile>) {
        userMobilesAdapter.updateData(mobileList)
    }

    private fun setListeners(){
        binding.addMobileButton.setOnClickListener {
            userMobilesAdapter.addMobile(Mobile("","Iphone","1.0.0",false))
        }

        binding.removeButton.setOnClickListener {
            userMobilesAdapter.deleteSelected()
        }
    }




}