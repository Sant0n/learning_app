package nnar.learning_app.userInterface.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    override fun onBackPressed() {
    }

    override fun updateData() {
        userMobilesAdapter.updateData()
    }

    override fun hideRemoveButton() {
        binding.removeButton.visibility = View.GONE
    }

    override fun hideAddButton() {
        binding.tabOptions.visibility = View.GONE
        binding.addMobileButton.visibility = View.GONE
    }

    override fun showRemoveButton() {
        binding.removeButton.visibility = View.VISIBLE
    }

    override fun showAddButton() {
        binding.tabOptions.visibility = View.VISIBLE
        binding.addMobileButton.visibility = View.VISIBLE
    }

    override fun clearSearchFocus() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        binding.searchText.clearFocus()
    }

    private fun setListeners(){
        binding.addMobileButton.setOnClickListener {
            presenter.addMobile(Mobile("", "Iphone", "1.0.0", false))
        }

        binding.removeButton.setOnClickListener {
            presenter.removeSelected()
        }

        binding.searchText.setOnEditorActionListener { _, i, _ ->
            presenter.searchMobile(i)
            true
        }

        binding.searchText.setOnFocusChangeListener { _, b ->
            presenter.checkKeyboardStatus(b)
        }
    }




}