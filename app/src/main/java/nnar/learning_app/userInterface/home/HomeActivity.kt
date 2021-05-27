package nnar.learning_app.userInterface.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.R
import nnar.learning_app.data.repository.MobileRepository
import nnar.learning_app.dataInterface.HomeView
import nnar.learning_app.databinding.ActivityHomeBinding
import nnar.learning_app.databinding.CustomDialogBinding
import nnar.learning_app.domain.usecase.HomeUserUsecase


class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var userMobilesAdapter: UserMobilesAdapter
    private lateinit var dialog: AlertDialog
    private lateinit var dialogBinding: CustomDialogBinding

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            presenter.onPermissionResult(granted)
        }
    private val pickFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            presenter.checkImagePicked(uri)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HomePresenter(this, HomeUserUsecase(MobileRepository()))

        binding.listOfMobiles.layoutManager = LinearLayoutManager(this)
        userMobilesAdapter = UserMobilesAdapter(presenter, this)
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
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchText.windowToken, 0)
        binding.searchText.clearFocus()
    }

    override fun openGallery() {
        pickFromGallery.launch("image/*")
    }

    override fun showNameNotFilled() {
        dialogBinding.mobileName.error = "Fill mobile name"
    }

    override fun showVersionNotFilled() {
        dialogBinding.mobileVersion.error = "Fill mobile version"
    }

    override fun clearFieldsError() {
        dialogBinding.mobileName.error = null
        dialogBinding.mobileVersion.error = null
    }

    override fun changeDialogImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(dialogBinding.mobileImage)

        dialogBinding.pencilPicture.visibility = View.GONE
    }

    override fun dismissDialog() {
        dialog.dismiss()
    }


    private fun setListeners() {

        binding.addMobileButton.setOnClickListener {
            val factory = LayoutInflater.from(this)
            val dialogView = factory.inflate(R.layout.custom_dialog, null)
            dialogBinding = CustomDialogBinding.bind(dialogView)
            dialog = AlertDialog.Builder(this).create()
            dialog.setTitle("Add a new mobile")
            dialog.setView(dialogView)

            dialogBinding.mobileImage.setOnClickListener {
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            /** FORMA CORRECTA: AÑADIR ONFOCUSCHANGE EN LOS EDITTEXT PARA ACTUALIZAR EN "REALTIME"*/
            dialogBinding.AddButton.setOnClickListener {
                presenter.addMobile(
                    dialogBinding.mobileName.text.toString(),
                    dialogBinding.mobileVersion.text.toString(),
                    dialogBinding.mobileImage.drawable.toBitmap()
                )
            }
            dialogBinding.cancelButton.setOnClickListener {
                dismissDialog()
            }

            dialog.show()
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