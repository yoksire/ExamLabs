package com.example.exampractice

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    private lateinit var etName:EditText
    private lateinit var etEmailId:EditText
    private lateinit var etPasswordL:EditText
    private lateinit var etPasswordC:EditText
    private lateinit var btnSignup:Button
    private lateinit var btnBack:ImageView
    private lateinit var auth:FirebaseAuth
    private lateinit var emailStr:String
    private lateinit var passwordStr:String
    private lateinit var nameStr:String
    private lateinit var passwordCStr:String
    private lateinit var progressDialog:Dialog
    private lateinit var dialogText:TextView
    private lateinit var context:Context




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        etName=findViewById(R.id.etName)
        etEmailId=findViewById(R.id.etEmailId)
        etPasswordL=findViewById(R.id.etPasswordL)
        etPasswordC=findViewById(R.id.etPasswordC)
        btnBack=findViewById(R.id.btnback)
        btnSignup=findViewById(R.id.btnSignup)
        context=this

        auth= FirebaseAuth.getInstance()
        progressDialog= Dialog(this)
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.setCancelable(false)
        progressDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogText=progressDialog.findViewById(R.id.dialog_text)
        dialogText.text="Registering User..."

        btnBack.setOnClickListener {
            finish()
        }
        btnSignup.setOnClickListener {
            if (validate()){
                signupNewUser()
            }

        }

    }
    private fun validate():Boolean{
        nameStr=etName.text.toString().trim()
        emailStr=etEmailId.text.toString().trim()
        passwordStr=etPasswordL.text.toString().trim()
        passwordCStr=etPasswordC.text.toString().trim()

        if(nameStr.isEmpty()){
            etName.error="Enter Name"
            return false
        }
        if(emailStr.isEmpty()){
            etEmailId.error="Enter E-mail"
            return false
        }
        if(passwordStr.isEmpty()){
            etPasswordL.error="Enter Password"
            return false
        }
        if(passwordCStr.isEmpty()){
            etPasswordC.error="Enter Password"
            return false
        }
        if(passwordStr.compareTo(passwordCStr)!=0){
            Toast.makeText(this,"Password Mismatch",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
    private fun signupNewUser(){
        progressDialog.show()
        auth.createUserWithEmailAndPassword(emailStr, passwordStr)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   Toast.makeText(this,"Sign Up Successful",Toast.LENGTH_SHORT).show()
                    val obj =object: MyCompleteListener{
                        override fun onSuccess() {
                            progressDialog.dismiss()
                            val i=Intent(context,MainActivity::class.java)
                            startActivity(i)
                            (context as SignupActivity).finish()
                        }

                        override fun onFailure() {
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()
                        }

                    }
                    DBQuery.createUserData(emailStr, nameStr,obj)


                } else {
                    progressDialog.dismiss()

                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }
}