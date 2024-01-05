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
        public val  g_testList:MutableList<TestModel> = ArrayList()
        public var g_selected_cat_index = 0
        public var g_selected_test_index = 0
        public var g_questionList:MutableList<QuestionModel> = ArrayList()
        public final val NOT_VISITED = 0
        public final val NOT_ANSWERED = 1
        public final val ANSWERED = 2
        public final val REVIEW = 3

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
                    val catCount:Long?= catListDoc!!.getLong("COUNT")
                    for(i in 1..catCount!!){
                        val catID = catListDoc.getString("CAT$i" +"_ID")
                        val catDoc:QueryDocumentSnapshot?= docList[catID]
                        val noOfTest= catDoc!!.getLong("NO_OF_TEST")!!.toInt()
                        val catName= catDoc.getString("NAME")
                        g_catList.add(CategoryModel(catID.toString(), catName.toString(),noOfTest))
                    }
                    completeListener.onSuccess()
                }
                .addOnFailureListener {
                    completeListener.onFailure()
                }

        }

        public fun loadTestData(completeListener: MyCompleteListener){
            g_testList.clear()
            g_firestore.collection("QUIZ").document(g_catList[g_selected_cat_index].getDocId())
                .collection("TESTS_LIST").document("TESTS_INFO")
                .get()
                .addOnSuccessListener {documentSnapshot->


                        val noOfTest = g_catList[g_selected_cat_index].getNumTest()
                        for (i in 1..noOfTest) {
                            val testId = documentSnapshot?.getString("TEST$i" + "_ID")
                            val testTime = documentSnapshot?.getLong("TEST$i" + "_TIME")?.toInt()
                            if (testId != null && testTime != null) {
                                g_testList.add(TestModel(testId, 20, testTime))
                            }

                        }
                        completeListener.onSuccess()
                    }
                .addOnFailureListener {
                    completeListener.onFailure()
                }
        }

        public fun loadQuestion(completeListener: MyCompleteListener){
            g_questionList.clear()
            g_firestore.collection("Questions")
                .whereEqualTo("CATEGORY", g_catList[g_selected_cat_index].getDocId())
                .whereEqualTo("TEST", g_testList[g_selected_test_index].getTestID())
                .get()
                .addOnSuccessListener { documentSnapshot->

                    for(doc in documentSnapshot){

                        g_questionList.add(QuestionModel(
                            doc.getString("QUESTION")!!,
                            doc.getString("A")!!,
                            doc.getString("B")!!,
                            doc.getString("C")!!,
                            doc.getString("D")!!,
                            doc.getLong("Answer")!!.toInt(),
                            0,
                            NOT_VISITED

                        ))

                    }
                    completeListener.onSuccess()

                }
                .addOnFailureListener {
                    completeListener.onFailure()
                }
        }

    }


}