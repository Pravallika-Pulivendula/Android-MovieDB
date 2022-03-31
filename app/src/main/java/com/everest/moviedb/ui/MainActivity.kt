package com.everest.moviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.everest.moviedb.R
import com.everest.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragment = HomeFragment()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        loadFragment()
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_one, HomeFragment())
            addToBackStack(null)
            commit()
        }
    }
}