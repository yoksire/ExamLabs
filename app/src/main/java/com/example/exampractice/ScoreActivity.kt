package com.example.exampractice

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.exampractice.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    private var timeTaken:Long=0
    private lateinit var progressDialog: Dialog
    private lateinit var dialogText:TextView
    private var finalScore:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityScoreBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar=binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Result"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressDialog= Dialog(this)
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.setCancelable(false)
        progressDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogText=progressDialog.findViewById(R.id.dialog_text)
        dialogText.text="Loading..."


        loadData()
        binding.btnReAttempt.setOnClickListener {
            reAttempt()
        }
        saveResult()
    }

    private fun saveResult() {
            val obj = object : MyCompleteListener {
                override fun onSuccess() {
                    progressDialog.dismiss()
                }

                override fun onFailure() {
                    Toast.makeText(this@ScoreActivity,"Failed to save result",Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()

                }

            }
            DBQuery.saveResult(finalScore,obj)
    }

    private fun reAttempt() {
        for (ques in DBQuery.g_questionList){
            ques.status= DBQuery.NOT_VISITED
            ques.selectedAnswer=0
        }
        var i = Intent(this,QuestionActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun loadData() {
        var correct =0
        var wrong =0
        var notAttempted:Int=0
        for (ques in DBQuery.g_questionList){
            when(ques.status){
                DBQuery.ANSWERED->{
                    if(ques.selectedAnswer==ques.correctAnswer){
                        correct++
                    }else{
                        wrong++
                    }
                }
                DBQuery.NOT_ANSWERED->{
                    notAttempted++
                }
            }
        }
        binding.tvCorrect.text=correct.toString()
        binding.tvWrong.text=wrong.toString()
        binding.tvUnAttempted.text=notAttempted.toString()
        binding.tvTotalQuestion.text=DBQuery.g_questionList.size.toString()
        finalScore=correct*4-wrong
        binding.tvScore.text=finalScore.toString()
        timeTaken=intent.getLongExtra("TIME_TAKEN",0)
        binding.tvTimeTaken.text=convertTime(timeTaken)
    }

    private fun convertTime(millisUntilFinished: Long): CharSequence? {
        val minutes=millisUntilFinished/1000/60
        val seconds=millisUntilFinished/1000%60
        val timeLeft:String=String.format("%02d:%02d min",minutes,seconds)
        return timeLeft
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            this.finish()
        }
        return super.onOptionsItemSelected(item)

    }
}