package com.example.exampractice

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView

class CategoryFragment : Fragment() {
        companion object{public  var catList:MutableList<CategoryModel> = ArrayList()}
    private lateinit var catView:GridView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_category, container, false)
        catView = view.findViewById(R.id.cat_grid)
        loadCategories()
        val adapter = CategoryAdapter(catList)
        catView.adapter=adapter
        return view
    }

    private fun loadCategories(){

        catList.clear()
        catList.add(CategoryModel("1","GK",20))
        catList.add(CategoryModel("2","LOGICAL REASONING",20))
        catList.add(CategoryModel("3","MATHS",20))
        catList.add(CategoryModel("4","CHEMISTRY",20))
        catList.add(CategoryModel("5","PHYSICS",20))
}
}