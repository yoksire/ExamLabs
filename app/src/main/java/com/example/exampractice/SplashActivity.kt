package com.example.exampractice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        context=this
        var appName=findViewById<TextView>(R.id.tvAppName)
        var typeface= ResourcesCompat.getFont(this,R.font.blacklistbolditalic)
        appName.typeface = typeface
        var anim = AnimationUtils.loadAnimation(this,R.anim.myanim)
        appName.animation=anim
        mAuth = FirebaseAuth.getInstance()
        val obj=object : MyCompleteListener {
            override fun onSuccess() {
                val i = Intent(context,MainActivity::class.java)
                startActivity(i)
            }

            override fun onFailure() {
                Toast.makeText(
                    context,
                    "Failed to load Data",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        DBQuery.g_firestore= Firebase.firestore
        if(mAuth.currentUser != null){
            appName.animate().setDuration(2500).alpha(1f).withEndAction{
                DBQuery.loadCategories(obj)
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
                finish()
            }

        }else{
            appName.animate().setDuration(2500).alpha(1f).withEndAction{
                val i = Intent(this,LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }



    }
}