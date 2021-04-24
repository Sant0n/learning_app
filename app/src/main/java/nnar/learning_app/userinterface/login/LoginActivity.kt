package nnar.learning_app.userinterface.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import nnar.learning_app.R
import nnar.learning_app.databinding.ActivityLoginBinding
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.userinterface.home.HomeActivity


class LoginActivity : AppCompatActivity(), LoginView {

    private val RC_SIGN_IN: Int = 1
    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        presenter = LoginPresenter(this)
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        presenter.checkActiveUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.checkActivityResult(requestCode, RC_SIGN_IN, data)
    }

    override fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                presenter.checkTask(task)
            }
    }

    override fun showErrorLogin() {
        Toast.makeText(applicationContext, R.string.login_error_msg, Toast.LENGTH_LONG).show()
    }

    override fun loginSuccessful() {
        val user = auth.currentUser
        Toast.makeText(applicationContext, user.displayName, Toast.LENGTH_LONG).show()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun setListeners() {
        binding.signInButton.setOnClickListener {
             signIn()
        }
    }
}