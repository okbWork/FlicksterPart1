package com.example.flicksterpart1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flicksterpart1.R.id

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id.content, FlicksterMovieFragment(), null).commit()
    }

}