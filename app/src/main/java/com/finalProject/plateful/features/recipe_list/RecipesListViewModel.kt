package com.finalProject.plateful.features.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe


class RecipesListViewModel: ViewModel() {
    var data: LiveData<MutableList<Recipe>> = RecipesRepository.shared.getAllRecipes()

    fun refreshRecipes() {
        RecipesRepository.shared.refreshRecipes()
    }
}