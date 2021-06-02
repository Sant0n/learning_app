package nnar.learning_app.userInterface.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nnar.learning_app.R
import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.dataInterface.LoginView
import nnar.learning_app.databinding.ActivityLoginBinding
import nnar.learning_app.domain.usecase.LoginUserUsecase
import nnar.learning_app.userInterface.home.HomeActivity

/**
 * Class that manages the google login to the app
 *
 * @author Jose Agustin Martin
 * @since Mar 14, 2021
 *
 */

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    /**
     * Inflate the login layout, initialize the presenter for MVP and set the listener for the google button.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        presenter = LoginPresenter(this, LoginUserUsecase(UserRepository()))
        setListeners()

    }

    /**
     * Check if the user has already logged in the app, if so then it login into the app with the previous account.
     */
    override fun onStart() {
        super.onStart()
        presenter.checkLoggedUser(auth.currentUser)
    }


    override fun googleSuccessful(idToken: String) =
        firebaseAuthWithGoogle(idToken)


    override fun googleError() =
        Toast.makeText(this, R.string.login_error_msg, Toast.LENGTH_LONG).show()


    /**
     * Show login error message.
     *
     * @param msg Error message from Google login.
     */
    override fun showErrorLogin(msg: String) {
        Toast.makeText(this, R.string.login_error_msg, Toast.LENGTH_LONG).show()
    }


    override fun loginSuccessful() {
        presenter.checkUserRegistered(auth.currentUser!!)
    }


    override fun moveToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user", auth.currentUser)
        startActivity(intent)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.googleLogin(RC_SIGN_IN, TAG, requestCode, resultCode, data)
    }


    private fun setListeners() {
        binding.googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                presenter.getUserWithCredential(task, TAG)
            }
    }

}