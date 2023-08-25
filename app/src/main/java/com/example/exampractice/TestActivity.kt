package com.example.exampractice

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TestActivity : AppCompatActivity() {
    private lateinit var testView:RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var  testList:MutableList<TestModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        testView=findViewById(R.id.test_recycler_view)
        toolbar=findViewById(R.id.toolbar)
        testList=ArrayList()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        val catIndex = intent.getIntExtra("CAT_INDEX",0)
        supportActionBar?.title = CategoryFragment.catList[catIndex].getName()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        testView =findViewById(R.id.test_recycler_view)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        testView.layoutManager = layoutManager

        loadTestData()
        val adapter= TestAdapter(testList)
        testView.adapter =adapter

    }
    private fun loadTestData(){
        testList.clear()
        testList.add(TestModel("1",90,60))
        testList.add(TestModel("2",90,60))
        testList.add(TestModel("3",90,60))
        testList.add(TestModel("4",180,120))
        testList.add(TestModel("5",180,120))
        testList.add(TestModel("6",180,120))
        testList.add(TestModel("7",300,20))
        testList.add(TestModel("8",300,20))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            this.finish()
        }
        return super.onOptionsItemSelected(item)

    }
}