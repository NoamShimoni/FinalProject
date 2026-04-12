package com.finalProject.plateful.data.repositories.recipes

import android.graphics.Bitmap
import android.util.Log
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseModel
import com.finalProject.plateful.models.Recipe

class RecipesRepository private constructor() {

    private val storageModel: CloudinaryStorageModel = CloudinaryStorageModel()
    private val firebaseModel = FirebaseModel()

    companion object {
        val shared = RecipesRepository()
    }

    fun addRecipe(recipeImage: Bitmap, recipe: Recipe, completion: Completion) {
        firebaseModel.addRecipe(recipe) {
            storageModel.uploadRecipeImage(recipeImage, recipe.id) { imageUrl ->
                if (!imageUrl.isNullOrEmpty()) {
                    val recipeCopy = recipe.copy(imageUrl = imageUrl)
                    firebaseModel.addRecipe(recipeCopy, completion)
                } else {
                    completion()
                }
            }
        }
    }
}