package com.example.exampractice

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exampractice.adapters.TestAdapter

class TestActivity : AppCompatActivity() {
    private lateinit var testView:RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var adapter: TestAdapter
    private lateinit var progressDialog:Dialog
    private lateinit var dialogText: TextView
    private lateinit var context: Context



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        testView=findViewById(R.id.test_recycler_view)
        toolbar=findViewById(R.id.toolbar)
        context=this


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        supportActionBar?.title = DBQuery.g_catList[DBQuery.g_selected_cat_index].getName()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        testView =findViewById(R.id.test_recycler_view)

        progressDialog= Dialog(this)
        progressDialog.setContentView(R.layout.dialog_layout)
        progressDialog.setCancelable(false)
        progressDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogText=progressDialog.findViewById(R.id.dialog_text)
        dialogText.text="Loading..."

        progressDialog.show()


        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        testView.layoutManager = layoutManager
        val obj = object : MyCompleteListener {
            override fun onSuccess() {
                val obj1=object :MyCompleteListener{
                    override fun onSuccess() {
                        adapter= TestAdapter(DBQuery.g_testList)
                        testView.adapter =adapter
                        progressDialog.dismiss()
                    }

                    override fun onFailure() {
                        progressDialog.dismiss()
                        Toast.makeText(
                            context,
                            "Something went wrong ! Please try again",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                }
                DBQuery.loadData(obj1)

            }

            override fun onFailure() {
                progressDialog.dismiss()
                Toast.makeText(
                    context,
                    "Something went wrong ! Please try again",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        DBQuery.loadTestData(obj)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            this.finish()
        }
        return super.onOptionsItemSelected(item)

    }
}