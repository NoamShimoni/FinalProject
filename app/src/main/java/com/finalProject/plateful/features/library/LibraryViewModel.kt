package com.finalProject.plateful.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.recipe_list.IRecipesViewModel
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


class LibraryViewModel: ViewModel(), IRecipesViewModel {
    override var data: LiveData<MutableList<Recipe>> = RecipesRepository.shared.getAllRecipes(Firebase.auth.currentUser?.uid)
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

    override fun isRecipeByCurrentUser(recipe: Recipe): Boolean {
        val currentUser = AuthRepository.shared.getCurrentUser()
        return recipe.creatingUserId == currentUser?.uid
    }
}