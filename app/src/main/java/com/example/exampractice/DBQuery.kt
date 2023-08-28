package com.example.exampractice

import android.annotation.SuppressLint
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.WriteBatch

class DBQuery {
    companion object{
        @SuppressLint("StaticFieldLeak")
        public lateinit var g_firestore:FirebaseFirestore
        public  var g_catList:MutableList<CategoryModel> = ArrayList()

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

        public fun loadCategories(completeListener: MyCompleteListener){
                g_catList.clear()
            val collectionRef = g_firestore.collection("QUIZ")
            collectionRef.get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    val docList= mutableMapOf<String,QueryDocumentSnapshot>()

                    for (doc in queryDocumentSnapshots) {

                        docList[doc.id] = doc

                        // Process the data as needed
                    }
                    val catListDoc: QueryDocumentSnapshot? = docList["categories"]
                    var catCount:Long?= catListDoc!!.getLong("COUNT")
                    for(i in 1..catCount!!){
                        var catID = catListDoc.getString("CAT$i" +"_ID")
                        var catDoc:QueryDocumentSnapshot?= docList[catID]
                        var noOfTest= catDoc!!.getLong("NO_OF_TEST")!!.toInt()
                        var catName= catDoc.getString("NAME")
                        g_catList.add(CategoryModel(catID.toString(), catName.toString(),noOfTest))
                    }
                    completeListener.onSuccess()
                }
                .addOnFailureListener {
                    completeListener.onFailure()
                }

        }

    }

}