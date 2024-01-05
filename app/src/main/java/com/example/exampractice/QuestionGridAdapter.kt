package com.example.exampractice

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuestionGridAdapter(private var context: Context, private var numOfQues: Int) : BaseAdapter() {
    override fun getCount(): Int {
        return numOfQues
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        if (convertView==null){
            view=LayoutInflater.from(parent?.context).inflate(R.layout.question_grid_item,parent,false)
        }else{
            view=convertView
        }

        view.setOnClickListener {
            if(context is QuestionActivity){
                (context as QuestionActivity).goToNextQuestion(position)
            }

        }

        val tvQuestionNum = view.findViewById<TextView>(R.id.tvQuestionNumber)
        tvQuestionNum.text = (position+1).toString()

        when(DBQuery.g_questionList[position].status){
            DBQuery.NOT_VISITED -> tvQuestionNum.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(parent!!.context,R.color.not_visited))
            DBQuery.NOT_ANSWERED -> tvQuestionNum.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(parent!!.context,R.color.not_answered))
            DBQuery.ANSWERED -> tvQuestionNum.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(parent!!.context,R.color.answered))
            DBQuery.REVIEW -> tvQuestionNum.backgroundTintList= ColorStateList.valueOf(ContextCompat.getColor(parent!!.context,R.color.review))

        }
        return view
    }
}