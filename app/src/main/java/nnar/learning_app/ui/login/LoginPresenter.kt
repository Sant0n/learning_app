package nnar.learning_app.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import nnar.learning_app.datainterface.LoginView
import nnar.learning_app.domain.model.UserResponse
import nnar.learning_app.domain.usecase.LoginUseCase

class LoginPresenter(private val view: LoginView, private val useCase: LoginUseCase): ViewModel() {

    internal fun googleLogin(RC_SIGN_IN: Int, requestCode:Int, TAG:String, resultCode:Int, data:Intent? ){
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                view.googleSuccessful(account.idToken!!)
                //firebaseAuthWithGoogle()
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                view.googleErrorLogin()
            }
        }
    }

    internal fun userCredentials(task: Task<AuthResult>, TAG: String){
        if (task.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success")
            view.successLogin()
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.exception)
            view.googleErrorLogin()
        }

    }

    internal fun checkUserGoogleRegistered(user: FirebaseUser?){
        viewModelScope.launch{
            if(user != null){
                useCase.checkUserGoogleRegistered(user)
            }
        }
    }

    internal fun checkLoggedUser(currentUser: FirebaseUser?){
        if(currentUser != null){
            view.successLogin()
        }
    }
    internal fun verifyUsername(username: String, hasFocus: Boolean){
        if(!hasFocus && username != ""){
            view.drawSuccessUsernameField()
        }
    }

    internal fun verifyPass(pass:String, hasFocus: Boolean){
        if(!hasFocus && pass != ""){
            view.drawSuccessPasswordField()
        }
    }

    internal fun verifyUser(user:String, pass: String) {
        val response: UserResponse = useCase.loginUser(user, pass)

        if (response.responseValue){
            view.drawSuccessUsernameField()
            view.drawSuccessPasswordField()
            view.showLoginResponse("Success Login")
            view.navigateToHome()
        }else{
            if(response.msg == "Wrong Password"){
                view.showErrorPassField("Wrong password")
            }else{
                view.showErrorNameField("Wrong username")
                view.showErrorPassField("Wrong password")
            }
        }
    }

}