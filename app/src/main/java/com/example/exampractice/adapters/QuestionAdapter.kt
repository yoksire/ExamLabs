package com.example.exampractice.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.DBQuery
import com.example.exampractice.DBQuery.Companion.ANSWERED
import com.example.exampractice.DBQuery.Companion.NOT_ANSWERED
import com.example.exampractice.R
import com.example.exampractice.model.QuestionModel

class QuestionAdapter(private var questionList:MutableList<QuestionModel>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val question=itemView.findViewById<TextView>(R.id.tvQuestion)
        val optionA=itemView.findViewById<Button>(R.id.optionA)
        val optionB=itemView.findViewById<Button>(R.id.optionB)
        val optionC=itemView.findViewById<Button>(R.id.optionC)
        val optionD=itemView.findViewById<Button>(R.id.optionD)
        var previousSelectedButton:Button?=null
        fun setData(pos:Int){
            question.text= DBQuery.g_questionList[pos].question
            optionA.text= DBQuery.g_questionList[pos].optionA
            optionB.text= DBQuery.g_questionList[pos].optionB
            optionC.text= DBQuery.g_questionList[pos].optionC
            optionD.text= DBQuery.g_questionList[pos].optionD

            setOption(optionA,1,pos)
            setOption(optionB,2,pos)
            setOption(optionC,3,pos)
            setOption(optionD,4,pos)

            optionA.setOnClickListener {
                selectOption(optionA,1,pos)
            }
            optionB.setOnClickListener {
                selectOption(optionB,2,pos)
            }
            optionC.setOnClickListener {
                selectOption(optionC,3,pos)
            }
            optionD.setOnClickListener {
                selectOption(optionD,4,pos)
            }
        }

        private fun setOption(btn: Button?, optionNum: Int, quesID: Int) {
            if(DBQuery.g_questionList[quesID].selectedAnswer==optionNum){
                btn?.setBackgroundResource(R.drawable.selected_btn)
                previousSelectedButton=btn
            }else{
                btn?.setBackgroundResource(R.drawable.unselected_btn)
            }
        }

        private fun selectOption(btn: Button?, optionNum: Int, quesID: Int) {
            if(previousSelectedButton==null){
                btn?.setBackgroundResource(R.drawable.selected_btn)
                DBQuery.g_questionList[quesID].selectedAnswer=optionNum
                changeStatus(quesID,ANSWERED)
                previousSelectedButton=btn

        }else{
            if(previousSelectedButton==btn){
                btn?.setBackgroundResource(R.drawable.unselected_btn)
                DBQuery.g_questionList[quesID].selectedAnswer=0
                changeStatus(quesID,NOT_ANSWERED)
                previousSelectedButton=null

        }else{
            previousSelectedButton?.setBackgroundResource(R.drawable.unselected_btn)
            btn?.setBackgroundResource(R.drawable.selected_btn)
            DBQuery.g_questionList[quesID].selectedAnswer=optionNum
            changeStatus(quesID,ANSWERED)
            previousSelectedButton=btn

        }
        }
        }

        private fun changeStatus(id: Int, status: Int) {
            if(DBQuery.g_questionList[id].status != DBQuery.REVIEW){
                DBQuery.g_questionList[id].status=status
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val myView:View= LayoutInflater.from(parent.context).inflate(R.layout.question_item_layout,parent,false)
        return ViewHolder(myView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(position)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }


}