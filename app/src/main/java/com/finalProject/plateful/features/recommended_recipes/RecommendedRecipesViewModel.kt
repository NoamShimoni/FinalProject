package com.finalProject.plateful.features.recommended_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.recipe_list.IRecipesViewModel
import com.finalProject.plateful.data.repositories.remote_recipes.RemoteRecipesRepository
import com.finalProject.plateful.models.Recipe
import com.finalProject.plateful.models.RemoteRecipe
import java.util.concurrent.Executors

class RecommendedRecipesViewModel : ViewModel(), IRecipesViewModel {
    private val _data = MutableLiveData<MutableList<Recipe>>()
    override var data: LiveData<MutableList<Recipe>> = _data
    override val isRefreshing = MutableLiveData<Boolean>()
    
    private val executor = Executors.newSingleThreadExecutor()

    override fun refreshRecipes() {
        isRefreshing.postValue(true)
        
        executor.execute {
            val remoteRecipes = RemoteRecipesRepository.shared.getRecipes()
            
            val mappedRecipes = remoteRecipes.meals?.map { it.toRecipe() }?.toMutableList() ?: mutableListOf()
            
            _data.postValue(mappedRecipes)
            isRefreshing.postValue(false)
        }
    }

    override fun deleteRecipe(recipe: Recipe) {
        // No-op for remote recipes
    }

    override fun isRecipeByCurrentUser(recipe: Recipe): Boolean {
        // We do not want edit/delete buttons for remote recommendations
        return false
    }

}
