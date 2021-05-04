package nnar.learning_app.ui.login

import android.content.Intent

import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import nnar.learning_app.R

import nnar.learning_app.data.repository.UserRepository
import nnar.learning_app.databinding.ActivityLoginBinding
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.usecase.LoginUseCase
import nnar.learning_app.ui.contactListMenu.ContactListActivity
import nnar.learning_app.utils.CommonFunctions

//import android.util.Log

class LoginActivity :  AppCompatActivity(), LoginView{


    //Firebase Auth variables
    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient


    private lateinit var binding: ActivityLoginBinding
    private lateinit var usernameText: TextView
    private lateinit var passwordText: TextView
    private lateinit var confirmButton: Button
    private lateinit var googleButton: SignInButton
    private lateinit var presenter: LoginPresenter

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]
        auth = Firebase.auth


        usernameText = binding.loginUsernameEdittext
        passwordText = binding.loginPasswordEdittext
        confirmButton = binding.loginConfirmButton
        googleButton = binding.signInButton

        presenter = LoginPresenter(this, LoginUseCase(UserRepository()))

        if(intent.getBooleanExtra("logout", true)){
            googleSignInClient.signOut()
        }

        setListeners()
    }

    private fun setListeners() {
        usernameText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyUsername(usernameText.text.toString(), hasFocus)
        }

        passwordText.setOnFocusChangeListener { _, hasFocus ->
            presenter.verifyPass(passwordText.text.toString(), hasFocus)
        }

        confirmButton.setOnClickListener {
            presenter.verifyUser(usernameText.text.toString(), passwordText.text.toString())
        }

        googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
       // presenter.checkLoggedUser(auth.currentUser)
        presenter.checkUserGoogleRegistered(auth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.googleLogin(RC_SIGN_IN, requestCode, TAG, resultCode, data)
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                presenter.userCredentials(task, TAG)
            }
    }
    // [END auth_with_google]

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, ContactListActivity::class.java)
        intent.putExtra("user",user)
        startActivity(intent)
    }

    /**
     * View functions
     **/

    override fun showLoginResponse(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToHome() {
        val intent = Intent(this, ContactListActivity::class.java)
        startActivity(intent)
    }

    override fun googleErrorLogin() {
        Toast.makeText(this, "Error at login", Toast.LENGTH_SHORT).show()
    }

    override fun googleSuccessful(idToken: String) {
        firebaseAuthWithGoogle(idToken)
    }

    override fun successLogin() {
        val user = auth.currentUser
        updateUI(user)
    }

    override fun showErrorNameField(s:String){
        usernameText.error = s
        usernameText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun showErrorPassField(s:String){
        passwordText.error = s
        passwordText.setBackgroundResource(R.drawable.textview_error_border)
    }

    override fun drawSuccessUsernameField() {
        CommonFunctions().resetError(usernameText)
        usernameText.setBackgroundResource(R.drawable.textview_success_border)
    }

    override fun drawSuccessPasswordField() {
        CommonFunctions().resetError(passwordText)
        passwordText.setBackgroundResource(R.drawable.textview_success_border)
    }

}