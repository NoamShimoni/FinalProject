package com.finalProject.plateful.features.edit_recipe

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe
import com.google.firebase.auth.FirebaseUser

class EditRecipeViewModel: ViewModel() {
    fun editRecipe(recipe: Recipe, imageBitmap: Bitmap?, completion: Completion) {
        RecipesRepository.shared.editRecipe(recipe, imageBitmap) {
            completion()
        }
    }
}