package com.example.testtaskandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtaskandroid.dagger.DaggerAppComponent
import com.example.testtaskandroid.dagger.MyApplication
import com.example.testtaskandroid.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}