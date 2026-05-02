package com.finalProject.plateful.features.recommended_recipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finalProject.plateful.base.recipe_list.IRecipesViewModel
import com.finalProject.plateful.data.repositories.remote_recipes.RemoteRecipesRepository
import com.finalProject.plateful.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendedRecipesViewModel : ViewModel(), IRecipesViewModel {
    override var data = MutableLiveData<MutableList<Recipe>>()
    override val isRefreshing = MutableLiveData<Boolean>()

    override fun refreshRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val remoteRecipes = RemoteRecipesRepository.shared.getRecipes()

            val mappedRecipes = remoteRecipes.meals?.map { it.toRecipe() }?.toMutableList() ?: mutableListOf()

            data.postValue(mappedRecipes)
            isRefreshing.postValue(false)
        }
    }

    override fun deleteRecipe(recipe: Recipe) {}

    override fun isRecipeByCurrentUser(recipe: Recipe): Boolean {
        return false
    }
}
