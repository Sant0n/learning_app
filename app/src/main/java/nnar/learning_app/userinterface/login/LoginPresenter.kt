package nnar.learning_app.userinterface.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nnar.learning_app.datainterface.LoginView

class LoginPresenter(private val view: LoginView) {

    fun checkActivityResult(requestCode: Int, RC_SIGN_IN: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("SUCCESS", "firebaseAuthWithGoogle:" + account.id)
                view.firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("ERROR", "Google sign in failed", e)
            }
        }
    }

    fun checkTask(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d("SUCCESS", "signInWithCredential:success")
            view.loginSuccessful()
        } else {
            // If sign in fails, display a message to the user.
            Log.w("ERROR", "signInWithCredential:failure", task.exception)
            view.showErrorLogin()
        }
    }

    fun checkActiveUser() {
        if (Firebase.auth.currentUser != null)
            view.loginSuccessful()
    }
}