package nnar.learning_app.userinterface.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nnar.learning_app.R
import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.databinding.ActivityRegisterBinding
import nnar.learning_app.datainterface.RegisterView
import nnar.learning_app.domain.usecase.RegisterUserUsecase
import nnar.learning_app.userinterface.login.LoginActivity

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter(this, RegisterUserUsecase(UserRepository()))
        setListeners()
    }

    override fun registerSuccessful() {
        Toast.makeText(applicationContext, R.string.register_successful, Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun registerError() {
        Toast.makeText(applicationContext, R.string.register_error, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessfulEmailBox() {
        binding.userEmail.clearError()
        binding.userEmail.setBackgroundResource(R.drawable.successful_operation)
    }

    override fun showErrorEmailBox() {
        binding.userEmail.error = this.resources.getString(R.string.email_format)
        binding.userEmail.setBackgroundResource(R.drawable.form_field_error)
    }

    override fun showSuccessfulPassBox() {
        binding.regUserPassword.clearError()
        binding.regRepUserPassword.clearError()
        binding.regUserPassword.setBackgroundResource(R.drawable.successful_operation)
        binding.regRepUserPassword.setBackgroundResource(R.drawable.successful_operation)
    }

    override fun showErrorPassBox() {
        binding.regUserPassword.error = this.resources.getString(R.string.error_pass)
        binding.regRepUserPassword.error = this.resources.getString(R.string.error_pass)
        binding.regUserPassword.setBackgroundResource(R.drawable.form_field_error)
        binding.regRepUserPassword.setBackgroundResource(R.drawable.form_field_error)
    }

    private fun EditText.clearError() {
        error = null
    }

    private fun setListeners() {
        binding.userEmail.setOnFocusChangeListener { _, b ->
            presenter.checkEmailValid(binding.userEmail.text.toString(), b)
        }
        binding.regUserPassword.setOnFocusChangeListener { _, b ->
            presenter.checkPasswords(
                    binding.regUserPassword.text.toString(),
                    binding.regRepUserPassword.text.toString(),
                    b
            )
        }
        binding.regRepUserPassword.setOnFocusChangeListener { _, b ->
            presenter.checkPasswords(
                    binding.regUserPassword.text.toString(),
                    binding.regRepUserPassword.text.toString(),
                    b
            )
        }
        binding.signUpButton.setOnClickListener {
            presenter.signUp(
                    binding.userEmail.text.toString(), binding.userUsername.text.toString(),
                    binding.regUserPassword.text.toString(), binding.regRepUserPassword.text.toString()
            )
        }
    }
}