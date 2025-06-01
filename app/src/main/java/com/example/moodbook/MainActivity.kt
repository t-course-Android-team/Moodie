package com.example.moodbook

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.feature_response.presentation.ResponseFragment
import com.example.feature_saved.presentation.SavedFragment
import com.example.feature_search.presentation.SearchFragment
import com.example.moodbook.databinding.ActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomMenu()
        val callback = onBackPressedDispatcher.addCallback(this) {
            val currentFragment = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.firstOrNull()
            when (currentFragment) {
                is SavedFragment -> {
                    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                    bottomNavigationView.selectedItemId = com.example.feature_search.R.id.nav_graph_search
                }
                is SearchFragment -> {
                    finish()
                }
                is ResponseFragment -> {

                }
            }
        }
    }

    private fun initBottomMenu() {
        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.containerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }

}


