package com.finalProject.plateful.data.repositories.recipes

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import com.finalProject.plateful.base.BooleanCompletion
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.dao.AppLocalDB
import com.finalProject.plateful.dao.AppLocalDbRepository
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseModel
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Timestamp
import java.util.concurrent.Executors
import android.os.Handler
import android.os.Looper

class RecipesRepository private constructor() {

    private val storageModel: CloudinaryStorageModel = CloudinaryStorageModel.shared
    private val firebaseModel = FirebaseModel()
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler.createAsync(Looper.getMainLooper())
    private val database: AppLocalDbRepository = AppLocalDB.db
    private val recipes: LiveData<MutableList<Recipe>>? = null

    companion object {
        val shared = RecipesRepository()
    }

    fun getAllRecipes(creatingUserId: String?): LiveData<MutableList<Recipe>> {
        return recipes ?: creatingUserId?.let { creatingUserId -> database.recipeDao.getAllRecipesByUser(creatingUserId) } ?: run { database.recipeDao.getAllRecipes() }
    }

    fun refreshRecipes() {
        val lastUpdated = Recipe.Companion.lastUpdated

        firebaseModel.getAllRecipes {
            executor.execute {
                var time = lastUpdated

                val delete = database.recipeDao.getAllRecipesSync().filter { recipe ->
                    !it.contains(recipe)
                }

                for (recipe in delete) {
                    database.recipeDao.deleteRecipeById(recipe.id)
                }

                val update = it.filter { recipe ->
                    (recipe.lastUpdated ?: (Timestamp.now().seconds * 1000)) >= lastUpdated
                }

                for (recipe in update) {
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

    fun editRecipe(recipe: Recipe, recipeImage: Bitmap?, completion: Completion) {
        Log.v("TAG", recipe.toString())
        firebaseModel.addRecipe(recipe) {
            Log.v("TAG", "added recipe")
            recipeImage?.let {
                storageModel.uploadRecipeImage(recipeImage, recipe.id) { imageUrl ->
                    if (!imageUrl.isNullOrEmpty()) {
                        Log.v("TAG", imageUrl)
                        val recipeCopy = recipe.copy(imageUrl = imageUrl)
                        firebaseModel.addRecipe(recipeCopy, completion)
                    } else {
                        Log.v("TAG", "failed")
                        completion()
                    }
                }
            } ?: run {
                completion()
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

    fun updateUserNameForRecipes(userId: String, newUserName: String, completion: BooleanCompletion) {
        firebaseModel.updateUserNameForRecipes(userId, newUserName) { isSuccess ->
            if (isSuccess) {
                executor.execute {
                    database.recipeDao.updateUserNameForRecipes(userId, newUserName)
                    mainHandler.post {
                        completion(true)
                    }
                }
            } else {
                completion(false)
            }
        }
    }
}