package com.finalProject.plateful.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finalProject.plateful.models.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM Recipe WHERE creatingUserId = :creatingUserId")
    fun getAllRecipesByUser(creatingUserId: String): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM Recipe")
    fun getAllRecipesSync(): MutableList<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(vararg recipes: Recipe)

    @Query("DELETE FROM Recipe WHERE id = :recipeId")
    fun deleteRecipeById(recipeId: String)

    @Query("UPDATE Recipe SET creatingUserName = :newUserName WHERE creatingUserId = :userId")
    fun updateUserNameForRecipes(userId: String, newUserName: String)
}