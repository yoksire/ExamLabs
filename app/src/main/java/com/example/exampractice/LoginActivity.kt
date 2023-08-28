package com.example.exampractice

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.wifi.hotspot2.pps.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.concurrent.CancellationException

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail:EditText
    private lateinit var etPsw:EditText
    private lateinit var btnLogin:Button
    private lateinit var tvForgotPsw:TextView
    private lateinit var tvSignUp:TextView
    private lateinit var progressDialog:Dialog
    private lateinit var dialogText:TextView
    private lateinit var context:Context
    private lateinit var auth:FirebaseAuth
    private lateinit var gSignB:RelativeLayout
    private lateinit var googleSignInClient :GoogleSignInClient

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail=findViewById(R.id.etEmail)
        etPsw=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        tvForgotPsw=findViewById(R.id.tvForgotPass)
        tvSignUp=findViewById(R.id.tvSignup)
        gSignB=findViewById(R.id.google_sigin)
         context=this
        progressDialog= Dialog(this)
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.setCancelable(false)
        progressDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogText=progressDialog.findViewById(R.id.dialog_text)
        dialogText.text="Signing In..."
        auth=FirebaseAuth.getInstance()
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_server_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)



        btnLogin.setOnClickListener {
            if(validateData()){
                login()
            }
        }
        tvSignUp.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        gSignB.setOnClickListener {
            lifecycleScope.launch {
                signInWithGoogle()
            }

        }


    }
    private fun signInWithGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
                if (task.isSuccessful){
                    val account:GoogleSignInAccount? = task.result
                    if(account!=null){
                        updateUI(account)
                    }

                }else{
                    Toast.makeText(this,"Sign In Failed",Toast.LENGTH_SHORT).show()

                }
    }
    private fun updateUI(account:GoogleSignInAccount){
        progressDialog.show()

        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{task->
                if(task.isSuccessful){

                    // Sign in success, update UI with the signed-in user's information

                    if(task.result.additionalUserInfo!!.isNewUser){
                        val user =auth.currentUser

                        val obj =object: MyCompleteListener{
                            override fun onSuccess() {

                                val obj1=object :MyCompleteListener{
                                    override fun onSuccess() {
                                        progressDialog.dismiss()
                                        Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show()
                                        val i=Intent(context,MainActivity::class.java)
                                        startActivity(i)
                                        (context as LoginActivity).finish()
                                    }

                                    override fun onFailure() {
                                        progressDialog.dismiss()
                                        Toast.makeText(
                                            context,
                                            "Something went wrong ! Please try again",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }

                                }
                                DBQuery.loadCategories(obj1)


                            }

                            override fun onFailure() {
                                progressDialog.dismiss()
                                Toast.makeText(
                                    context,
                                    "Something went wrong ! Please try again",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                        }
                        if (user != null) {
                            DBQuery.createUserData(user.email.toString() , user.displayName.toString(), obj )

                        }
                    }else{

                        val obj =object: MyCompleteListener{
                            override fun onSuccess() {
                                progressDialog.dismiss()
                                val i=Intent(context,MainActivity::class.java)
                                startActivity(i)
                                Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show()
                                (context as LoginActivity).finish()
                            }

                            override fun onFailure() {
                                progressDialog.dismiss()
                                Toast.makeText(
                                    context,
                                    "Something went wrong ! Please try again",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                        }
                        DBQuery.loadCategories(obj)
                    }

                }
            else{
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
        }
    }

    private fun validateData():Boolean{


        if (etEmail.text.toString().isEmpty()){
            etEmail.error = "Enter E-mail Id"
            return false
        }
        if(etPsw.text.toString().isEmpty()){
            etPsw.error="Enter Password"
            return false
        }
        return true
    }
    private fun login(){
        progressDialog.show()
        auth.signInWithEmailAndPassword(etEmail.text.toString().trim(), etPsw.text.toString().trim())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show()
                    val obj1=object :MyCompleteListener{
                        override fun onSuccess() {
                            progressDialog.dismiss()

                            var i=Intent(context,MainActivity::class.java)
                            startActivity(i)
                        }

                        override fun onFailure() {
                            progressDialog.dismiss()
                            Toast.makeText(
                                context,
                                "Something went wrong ! Please try again",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                    }
                    DBQuery.loadCategories(obj1)



                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

    }





}