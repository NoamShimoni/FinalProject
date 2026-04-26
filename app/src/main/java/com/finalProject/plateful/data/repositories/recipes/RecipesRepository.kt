package com.finalProject.plateful.data.repositories.recipes

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.dao.AppLocalDB
import com.finalProject.plateful.dao.AppLocalDbRepository
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseModel
import com.finalProject.plateful.models.Recipe
import java.util.concurrent.Executors

class RecipesRepository private constructor() {

    private val storageModel: CloudinaryStorageModel = CloudinaryStorageModel.shared
    private val firebaseModel = FirebaseModel()
    private val executor = Executors.newSingleThreadExecutor()
    private val database: AppLocalDbRepository = AppLocalDB.db
    private val recipes: LiveData<MutableList<Recipe>>? = null

    companion object {
        val shared = RecipesRepository()
    }

    fun getAllRecipes(): LiveData<MutableList<Recipe>> {
        return recipes ?: database.recipeDao.getAllRecipes()
    }

    fun refreshRecipes() {
        val lastUpdated = Recipe.Companion.lastUpdated

        firebaseModel.getAllRecipes(lastUpdated) {
            executor.execute {
                var time = lastUpdated

                for (recipe in it) {
                    database.recipeDao.insertRecipes(recipe)
                    recipe.lastUpdated?.let { recipeLastUpdated ->
                        if (time < recipeLastUpdated) {
                            time = recipeLastUpdated
                        }
                    }
                }

                Recipe.Companion.lastUpdated = time
            }
        }
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

    fun deleteRecipe(recipe: Recipe, completion: Completion) {
        firebaseModel.deleteRecipe(recipe) {
            storageModel.deleteRecipeImage(recipe.imageUrl) { deleteImageSuccessful ->
                if (!deleteImageSuccessful) {
                    Log.v("TAG", "Error deleting recipe image for recipe: ${recipe.id}")
                }

                completion()
            }
        }
    }
}