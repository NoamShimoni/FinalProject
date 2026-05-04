package com.finalProject.plateful.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.finalProject.plateful.models.Recipe

@Database(entities = [Recipe::class], version = 6)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract val recipeDao: RecipeDao
}