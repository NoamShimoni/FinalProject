package com.finalProject.plateful.features.add_recipe

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe
import com.google.firebase.auth.FirebaseUser

class AddRecipeViewModel: ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }

    fun addRecipe(bitmap: Bitmap, recipe: Recipe, completion: Completion) {
        RecipesRepository.shared.addRecipe( bitmap, recipe, completion)
    }
}