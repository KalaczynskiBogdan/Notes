package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var imageHome: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        val fragment = NotesFragment()
        navigateToNextScreen(fragment)
        imageHome.setOnClickListener {
            navigateToNextScreen(fragment)
        }
    }

    private fun initViews() {
        imageHome = findViewById(R.id.tvHome)
    }

    fun navigateToNextScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}