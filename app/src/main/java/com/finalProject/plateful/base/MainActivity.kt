package com.finalProject.plateful.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.finalProject.plateful.R
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setupNavigation()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        setupBottomBar()

        setupTopBar()

    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainNavHost) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph)

        if (this.shouldNavigateToSignIn()) {
            navGraph.setStartDestination(R.id.signInFragment)
        } else {
            navGraph.setStartDestination(R.id.recipeListFragment)
        }

        navController?.graph = navGraph
    }

    private fun setupTopBar() {
        navController?.let {
            val appBarConfiguration = androidx.navigation.ui.AppBarConfiguration(
                setOf(R.id.recipeListFragment, R.id.profileFragment, R.id.libraryFragment, R.id.signInFragment)
            )
            binding?.topAppBar?.let { toolbar ->
                toolbar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.top_bar_menu_add -> {
                            navController?.navigate(R.id.action_global_addRecipeFragment)
                            true
                        }
                        else -> false
                    }
                }

                NavigationUI.setupWithNavController(toolbar, it, appBarConfiguration)

                it.addOnDestinationChangedListener { _, destination, _ ->
                    val addMenuItem = toolbar.menu.findItem(R.id.top_bar_menu_add)
                    addMenuItem?.isVisible = destination.id != R.id.addRecipeFragment && destination.id != R.id.signInFragment && destination.id != R.id.signUpFragment
                }
            }
        }
    }

    private fun setupBottomBar() {
        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottom_navigation_menu_home -> {
                    navController?.navigate(R.id.recipeListFragment)
                    true
                }
                R.id.bottom_navigation_menu_profile -> {
                    navController?.navigate(R.id.action_global_profileFragment)
                    true
                }
                R.id.bottom_navigation_menu_library -> {
                    navController?.navigate(R.id.libraryFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun shouldNavigateToSignIn(): Boolean {
        return !AuthRepository.shared.isUserSignedIn()
    }
}