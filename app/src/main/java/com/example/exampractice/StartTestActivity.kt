package com.example.exampractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.exampractice.databinding.ActivityStartTestBinding

class StartTestActivity : AppCompatActivity() {
    lateinit var binding:ActivityStartTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}