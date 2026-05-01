package com.finalProject.plateful.features.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.recipe_list.IRecipesViewModel
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe


class RecipesListViewModel: ViewModel(), IRecipesViewModel {
    override var data: LiveData<MutableList<Recipe>> = RecipesRepository.shared.getAllRecipes(null)
    override val isRefreshing = MutableLiveData<Boolean>()

    override fun refreshRecipes() {
        RecipesRepository.shared.refreshRecipes {
            isRefreshing.postValue(false)
        }
    }

    override fun deleteRecipe(recipe: Recipe) {
        RecipesRepository.shared.deleteRecipe(recipe) {
            refreshRecipes()
        }
    }
}