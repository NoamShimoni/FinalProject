package com.finalProject.plateful.base

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
    private val viewModel: MainActivityViewModel by viewModels()
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

        val navGraph = navController?.navInflater?.inflate(R.navigation.nav_graph)

        if (this.shouldNavigateToSignIn()) {
            navGraph?.setStartDestination(R.id.signInFragment)
        } else {
            navGraph?.setStartDestination(R.id.recipeListFragment)
        }

        navGraph?.let { navGraph ->
            navController?.graph = navGraph
        }
    }

    private fun setupTopBar() {
        navController?.let {
            val appBarConfiguration = androidx.navigation.ui.AppBarConfiguration(
                setOf(
                    R.id.recipeListFragment,
                    R.id.profileFragment,
                    R.id.libraryFragment,
                    R.id.signInFragment
                )
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
                    addMenuItem?.isVisible =
                        destination.id != R.id.addRecipeFragment && destination.id != R.id.signInFragment && destination.id != R.id.signUpFragment
                }
            }
        }
    }

    private fun setupBottomBar() {
        binding?.bottomNavigation?.let { bottomNavigationView ->
            navController?.let { navController ->
                NavigationUI.setupWithNavController(bottomNavigationView, navController)
            }
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.signUpFragment -> {
                    binding?.bottomNavigation?.visibility = View.GONE
                }
                else -> {
                    binding?.bottomNavigation?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun shouldNavigateToSignIn(): Boolean {
        return !viewModel.isUserSignedIn()
    }
}