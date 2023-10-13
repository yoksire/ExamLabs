package com.example.exampractice

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.exampractice.databinding.ActivityStartTestBinding

class StartTestActivity : AppCompatActivity() {
    private lateinit var progressDialog:Dialog
    private lateinit var dialogText: TextView
    lateinit var binding:ActivityStartTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        progressDialog= Dialog(this)
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.setCancelable(false)
        progressDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogText=progressDialog.findViewById(R.id.dialog_text)
        dialogText.text="Loading..."

        progressDialog.show()

        loadquestionActivity()
    }

    private fun loadquestionActivity() {
        val obj = object:MyCompleteListener{
            override fun onSuccess() {
                setData()
                progressDialog.dismiss()
            }

            override fun onFailure() {
                progressDialog.dismiss()
                Toast.makeText(
                    this@StartTestActivity,
                    "Something went wrong ! Please try again",
                    Toast.LENGTH_SHORT,
                ).show()
            }

        }

        DBQuery.loadQuestion(obj)
    }

    private fun setData() {
        binding.tvCategory.text= DBQuery.g_catList[DBQuery.g_selected_cat_index].getName()
        val sTN = "Test No. ${(DBQuery.g_selected_test_index+1).toString()}"
        val sTQ = DBQuery.g_questionList.size.toString()
        val sBS = DBQuery.g_testList[DBQuery.g_selected_test_index].getTopScore().toString()
        val sTime= DBQuery.g_testList[DBQuery.g_selected_test_index].getTime().toString()
        binding.tvTestNo.text=sTN
        binding.tvTestNum.text=sTQ
        binding.tvTestScore.text=sBS
        binding.tvDuration.text= sTime
    }

    private fun init(){
        binding.ivBack2.setOnClickListener {
            finish()
        }
        binding.btnStart.setOnClickListener {
            val i = Intent(this@StartTestActivity,TestActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}