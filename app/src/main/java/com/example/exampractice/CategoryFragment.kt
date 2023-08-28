package com.example.exampractice

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView

class CategoryFragment : Fragment() {

    private lateinit var catView:GridView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_category, container, false)
        catView = view.findViewById(R.id.cat_grid)
        //loadCategories()
        val adapter = CategoryAdapter(DBQuery.g_catList)
        catView.adapter=adapter
        return view
    }


}