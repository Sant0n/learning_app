package nnar.learning_app.userInterface.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import nnar.learning_app.dataInterface.LoginView
import nnar.learning_app.domain.usecase.LoginUserUsecase

class LoginPresenter(private val view: LoginView, private val loginUserUseCase: LoginUserUsecase) {

    internal fun googleLogin(
        RC_SIGN_IN: Int, TAG: String, requestCode: Int, resultCode: Int, data: Intent?
    ) {

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                view.googleSuccessful(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                view.googleError()
            }
        }
    }

    internal fun checkLoggedUser(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            view.loginSuccessful()
        }
    }

    internal fun getUserWithCredential(task: Task<AuthResult>, TAG: String){
        if (task.isSuccessful){
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success")
            view.loginSuccessful()
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.exception)
            view.showErrorLogin("signInWithCredential:failure")
        }
    }

    internal fun checkUserRegistered(user: FirebaseUser){
        val userRef = loginUserUseCase.checkUserRegistered(user.uid)
        userRef.get().addOnSuccessListener{doc ->
            if(doc.exists()){
                view.moveToHome()
            } else {
                val creationRef = loginUserUseCase.registerUser(user)
                creationRef.addOnSuccessListener {
                    view.moveToHome()

                }.addOnFailureListener {e ->
                    view.showErrorLogin(e.toString())
                }
            }

        }.addOnFailureListener {
            println("Error")
        }
    }

}