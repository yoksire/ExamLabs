package com.example.exampractice

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.exampractice.DBQuery.Companion.ANSWERED
import com.example.exampractice.DBQuery.Companion.NOT_ANSWERED
import com.example.exampractice.DBQuery.Companion.NOT_VISITED
import com.example.exampractice.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    lateinit var binding : ActivityQuestionBinding
    var QuestionId:Int=0
    var questionAdapter:QuestionAdapter?=null
    private lateinit var dialogBox: Dialog
    private lateinit var btnClose:ImageButton
    private lateinit var gridAdapter:QuestionGridAdapter
    private lateinit var gvQuestionView:GridView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this@QuestionActivity)
            .load(R.drawable.bookmark)
//            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.ivBookmark2)

        gridAdapter= QuestionGridAdapter(this,DBQuery.g_questionList.size)


        dialogBox= Dialog(this)
        dialogBox.setContentView(R.layout.question_list)
        dialogBox.setCancelable(false)

        gvQuestionView=dialogBox.findViewById(R.id.gvQuestionList)
        btnClose=dialogBox.findViewById(R.id.ibClose)

        gvQuestionView.adapter=gridAdapter

        dialogBox.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        btnClose.setOnClickListener {
            dialogBox.dismiss()
        }


        questionAdapter=QuestionAdapter(DBQuery.g_questionList)
        binding.questionView.adapter=questionAdapter
        val layoutManager= LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        binding.questionView.layoutManager=layoutManager




        this.init()
        setSnapHelper()
        setClickListeners()
        startTimer()

    }
    fun goToNextQuestion(pos:Int) {
       binding.questionView.smoothScrollToPosition(pos)
        if(dialogBox.isShowing){
            dialogBox.dismiss()
        }
    }

    private fun startTimer() {
        val totalTime=DBQuery.g_testList[DBQuery.g_selected_test_index].getTime()*60*1000L
        val timer:CountDownTimer=object :CountDownTimer(totalTime,1000){
            override fun onTick(millisUntilFinished: Long) {
                val minutes=millisUntilFinished/1000/60
                val seconds=millisUntilFinished/1000%60
                val timeLeft:String=String.format("%02d:%02d min",minutes,seconds)
                binding.tvTimer.text=timeLeft
            }

            override fun onFinish() {
                binding.tvTimer.text="00:00"
            }

        }
        timer.start()
    }

    private fun setClickListeners() {
        binding.btnRight.setOnClickListener {
            if(QuestionId<DBQuery.g_questionList.size-1){
                QuestionId++
                binding.questionView.smoothScrollToPosition(QuestionId)
            }
        }
        binding.btnLeft.setOnClickListener {
            if(QuestionId>0){
                QuestionId--
                binding.questionView.smoothScrollToPosition(QuestionId)
            }
        }
        binding.btnClear.setOnClickListener {
            DBQuery.g_questionList[QuestionId].selectedAnswer=0
            DBQuery.g_questionList[QuestionId].status=NOT_ANSWERED
            questionAdapter!!.notifyDataSetChanged()
        }
        binding.btnMarkReview.setOnClickListener {
            if(binding.ivBookmark2.visibility!= View.VISIBLE){
                binding.ivBookmark2.visibility=View.VISIBLE
                DBQuery.g_questionList[QuestionId].status=DBQuery.REVIEW
            }else{
                binding.ivBookmark2.visibility=View.GONE
                if(DBQuery.g_questionList[QuestionId].selectedAnswer!=0){
                    DBQuery.g_questionList[QuestionId].status=ANSWERED

                }else{
                    DBQuery.g_questionList[QuestionId].status= NOT_ANSWERED
                }
            }
        }
        binding.ivGridView.setOnClickListener {
            gridAdapter.notifyDataSetChanged()
            dialogBox.show()
        }

    }

    private fun init(){
        QuestionId=0
        binding.tvQnum.text="1/${DBQuery.g_questionList.size}"
        binding.tvQCat.text=DBQuery.g_catList[DBQuery.g_selected_cat_index].getName()
        DBQuery.g_questionList[0].status=NOT_ANSWERED



    }

    private fun setSnapHelper() {
        val snapHelper=PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.questionView)
        binding.questionView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val view=snapHelper.findSnapView(recyclerView.layoutManager)
                QuestionId=recyclerView.layoutManager!!.getPosition(view!!)
                if(DBQuery.g_questionList[QuestionId].status==NOT_VISITED){
                    DBQuery.g_questionList[QuestionId].status=NOT_ANSWERED
                }
                binding.tvQnum.text="${QuestionId+1}/${DBQuery.g_questionList.size}"
            }

        })
}
}