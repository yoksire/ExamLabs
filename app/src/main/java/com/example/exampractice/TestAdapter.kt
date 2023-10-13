package com.example.exampractice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.databinding.ActivityTestBinding

class TestAdapter(private var testList: MutableList<TestModel>) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var myView:View= LayoutInflater.from(parent.context).inflate(R.layout.test_item_layout,parent,false)
        return ViewHolder(myView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pgr = testList[position].getTopScore()
        holder.setData(position,pgr)
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var testNum= itemView.findViewById<TextView>(R.id.testNo)
        private var topScore= itemView.findViewById<TextView>(R.id.scoreText)
        private var progressBar= itemView.findViewById<ProgressBar>(R.id.testProgressBar)

        fun setData(pos:Int,pgr:Int){
            testNum.text= "Test No: ${pos + 1}"
            topScore.text = "$pgr %"
            progressBar.progress = pgr

            itemView.setOnClickListener {

                    DBQuery.g_selected_test_index=pos
                    val i= Intent(itemView.context,StartTestActivity::class.java)
                    itemView.context.startActivity(i)

            }

        }

    }
}