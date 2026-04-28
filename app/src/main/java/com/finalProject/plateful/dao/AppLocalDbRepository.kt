package com.finalProject.plateful.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.finalProject.plateful.models.Recipe

@Database(entities = [Recipe::class], version = 3)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract val recipeDao: RecipeDao
}