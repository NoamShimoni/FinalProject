package com.finalProject.plateful.base.recipe_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finalProject.plateful.models.Recipe

interface IRecipesViewModel {
    val data: LiveData<MutableList<Recipe>>
    val isRefreshing: MutableLiveData<Boolean>
    fun refreshRecipes()
    fun deleteRecipe(recipe: Recipe)
}
