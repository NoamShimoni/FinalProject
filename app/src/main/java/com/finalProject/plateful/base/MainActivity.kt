package com.finalProject.plateful.base

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.finalProject.plateful.R
import com.finalProject.plateful.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHost) as? NavHostFragment
        navController = navHostFragment?.navController

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.main_menu_home -> {
                    Log.v("MainActivity", "Home menu item selected")
                    navController?.navigate(R.id.recipeListFragment)
                    true
                }
                else -> false
            }
        }
    }
}