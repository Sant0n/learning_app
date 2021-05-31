package nnar.learning_app.ui.contactCreation

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import nnar.learning_app.R
import nnar.learning_app.data.repository.ContactFirestoreRepository
import nnar.learning_app.databinding.ActivityContactCreationBinding
import nnar.learning_app.datainterface.ContactCreationView
import nnar.learning_app.domain.usecase.ContactFirestoreUseCase
import nnar.learning_app.utils.CommonFunctions
import nnar.learning_app.utils.NotificationChannel

class ContactCreationActivity: AppCompatActivity(), ContactCreationView {

    private lateinit var binding: ActivityContactCreationBinding
    private lateinit var presenter: ContactCreationPresenter
    private lateinit var button: Button
    private lateinit var contactImage: ImageView
    private lateinit var contactName:TextView
    private lateinit var contactEmail:TextView
    private lateinit var contactPhone:TextView
    private var filePath: Uri? = null

    private val notification_id = 101

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted ->
        presenter.permissionResult(granted)
    }

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        presenter.verifyImage(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactCreationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        button = binding.contactCreationButton
        contactImage = binding.contactCreationImage
        contactName = binding.contactCreationEditTextName
        contactEmail = binding.contactCreationEditTextEmail
        contactPhone = binding.contactCreationEditTextPhone

        presenter = ContactCreationPresenter(this,
            ContactFirestoreUseCase(ContactFirestoreRepository()))

        setListeners()
    }

    private fun setListeners(){

        contactImage.setOnClickListener {
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        contactImage.setOnFocusChangeListener { _, _ ->
            presenter.verifyImage(filePath!!)
        }
/*
        contactName.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyName(contactName.text.toString(), hasFocus, contactName.text.isNotBlank())
        }

        contactEmail.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyEmail(contactEmail.text.toString(), hasFocus, contactEmail.text.isNotBlank())
        }

        contactPhone.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyPhone(contactPhone.text.toString(), hasFocus, contactPhone.text.isNotBlank())
        }
*/
        button.setOnClickListener {
            presenter.saveContact(filePath, contactName.text.toString(), contactEmail.text.toString(), contactPhone.text.toString())
        }
    }

    override fun showImage(uri: Uri){
        Glide.with(this)
            .load(uri)
            .into(contactImage)

        filePath = uri
    }

    override fun permissionResult(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorNameField(s: String) {
        contactName.error = s
        //contactName.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorEmailField(s: String) {
        contactEmail.error = s
        //contactEmail.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorPhoneField(s: String) {
        contactPhone.error = s
        //contactPhone.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorImageField(s: String){
        contactImage.setBackgroundResource(R.drawable.textview_error_border)
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessImageField() {
        contactImage.background = null
    }

    override fun showSuccessEmailField() {
        CommonFunctions().resetError(contactEmail)
        contactEmail.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun showSuccessNameField() {
        CommonFunctions().resetError(contactName)
        contactName.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun showSuccessPhoneField() {
        CommonFunctions().resetError(contactPhone)
        contactPhone.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun openGallery() {
        selectImage.launch("image/*")
    }


    override fun createNotification(){
        //val intent = Intent(this, ContactListActivity::class.java)
        //val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channel_id = NotificationChannel().createNotificationChannel(this)
        val builder = NotificationCompat.Builder(applicationContext, channel_id)
            .setSmallIcon(R.drawable.ic_baseline_account_circle_24)
            .setContentTitle("New contact added")
            //.setContentIntent(pendingIntent)
            .setContentText("${contactName.text} is a new contact in your list")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

      with(NotificationManagerCompat.from(this)){
           notify(notification_id, builder.build())
        }
    }

    override fun finishActivity() {
        this.finish()
    }
}