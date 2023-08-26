package com.example.exampractice

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch

class DBQuery {
    companion object{
        @SuppressLint("StaticFieldLeak")
        public lateinit var g_firestore:FirebaseFirestore

        public fun  createUserData(email: String, name: String, obj: MyCompleteListener){
            val userdata = mutableMapOf<String,Any>()
            userdata["EMAIL_ID"]=email
            userdata["NAME"]=name
            userdata["TOTAL_SCORE"]= 0
            val useDoc:DocumentReference= g_firestore.collection("USERS").document(FirebaseAuth.getInstance().currentUser!!.uid)
            val batch:WriteBatch= g_firestore.batch()
            batch.set(useDoc,userdata)
            val countDoc:DocumentReference= g_firestore.collection("USERS").document("TOTAL_USERS")
            batch.update(countDoc,"COUNT",FieldValue.increment(1))
            batch.commit().addOnSuccessListener {
                    obj.onSuccess()
            }
                .addOnFailureListener {
                    obj.onFailure()
                }

        }


    }

}