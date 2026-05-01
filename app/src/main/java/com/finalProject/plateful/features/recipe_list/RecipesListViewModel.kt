package com.finalProject.plateful.features.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe
import com.google.firebase.auth.FirebaseUser


class RecipesListViewModel: ViewModel() {
    var data: LiveData<MutableList<Recipe>> = RecipesRepository.shared.getAllRecipes(null)

    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }

    fun refreshRecipes() {
        RecipesRepository.shared.refreshRecipes()
    }

    fun deleteRecipe(recipe: Recipe) {
        RecipesRepository.shared.deleteRecipe(recipe) {
            refreshRecipes()
        }
    }
}