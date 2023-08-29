package com.example.exampractice

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CategoryAdapter(private var cat_list: List<CategoryModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return cat_list.size
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val myView:View=LayoutInflater.from(parent!!.context).inflate(R.layout.cat_item_layout,parent,false)
        myView.setOnClickListener {
            DBQuery.g_selected_cat_index=position
            val i = Intent(convertView?.context,TestActivity::class.java)
            convertView?.context?.startActivity(i)
        }
        val catName =myView.findViewById<TextView>(R.id.catName)
        val numTest = myView.findViewById<TextView>(R.id.numTest)
        catName.text = cat_list[position].getName()
        numTest.text= cat_list[position].getNumTest().toString()+" Tests"

        return myView
    }
}