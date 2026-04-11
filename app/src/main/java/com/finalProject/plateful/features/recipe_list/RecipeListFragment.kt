package com.finalProject.plateful.features.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.finalProject.plateful.databinding.FragmentRecipeListBinding
import com.finalProject.plateful.models.Recipe

class RecipeListFragment : Fragment() {
    private var binding: FragmentRecipeListBinding? = null
    private val viewModel: RecipesListViewModel by viewModels()
    private var adapter: RecipesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeListBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        refreshRecipes()
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layout

        binding?.recyclerView?.setHasFixedSize(true)

        adapter = RecipesAdapter(viewModel.data.value)

        adapter?.listener = object: OnItemClickListener {

            override fun onRecipeItemClick(recipe: Recipe) {
                navigateToRecipeCardFragment(recipe)
            }
        }

        binding?.recyclerView?.adapter = adapter

        binding?.swipeRefresh?.setOnRefreshListener {
            binding?.swipeRefresh?.isRefreshing = true // check if there are no new student while refreshing, so the swipeRefresh will stuck on isRefreshing = true
            refreshRecipes()
        }

        observeStudents()

    }

    private fun observeStudents() {
        viewModel.data.observe(viewLifecycleOwner) {
            adapter?.recipes = it
            adapter?.notifyDataSetChanged()
            binding?.swipeRefresh?.isRefreshing = false

            binding?.recipeCountSubtitle?.text = "${it.size} recipes"
        }
    }

    private fun refreshRecipes() {
        viewModel.refreshRecipes()
    }

    private fun navigateToRecipeCardFragment(recipe: Recipe){
        view?.let {
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeCardFragment(recipe.title, recipe.ingredients, recipe.instructions, recipe.imageUrl)
            Navigation.findNavController(it).navigate(action)
        }
    }
}