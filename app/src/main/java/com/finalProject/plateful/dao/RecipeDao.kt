package com.finalProject.plateful.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.finalProject.plateful.models.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM Recipe WHERE creatingUserId = :creatingUserId")
    fun getAllRecipesByUser(creatingUserId: String): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM Recipe")
    fun getAllRecipesSync(): MutableList<Recipe>

    @Upsert
    fun upsertRecipes(vararg recipes: Recipe)

    @Query("DELETE FROM Recipe WHERE id = :recipeId")
    fun deleteRecipeById(recipeId: String)

    @Query("DELETE FROM Recipe")
    fun deleteRecipes()
}