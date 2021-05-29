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
    // Variables for the View
    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter

    // Variables for Google Sign In
    private val RC_SIGN_IN: Int = 1
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // Create the Login Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        if (intent.getBooleanExtra("logout", false))
            googleSignInClient.signOut()

        presenter = LoginPresenter(this)
        setListeners()
    }

    // Start the login activity
    override fun onStart() {
        super.onStart()
        presenter.checkActiveUser()
    }

    // Return from the Google Sign In menu
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.checkActivityResult(requestCode, RC_SIGN_IN, data)
    }

    // Authenticate token
    override fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            presenter.checkTask(task)
        }
    }

    // Show error message
    override fun showErrorLogin() {
        Toast.makeText(applicationContext, R.string.login_error_msg, Toast.LENGTH_LONG).show()
    }

    // Go to Main Activity
    override fun loginSuccessful() {
        val user = auth.currentUser
        Toast.makeText(applicationContext, user!!.displayName, Toast.LENGTH_LONG).show()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    // Sign in on Google
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Set Sign In listener
    private fun setListeners() {
        binding.signInButton.setOnClickListener {
            signIn()
        }
    }
}