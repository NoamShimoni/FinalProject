package com.finalProject.plateful.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finalProject.plateful.models.Recipe

@Dao
interface RecipeDao {
        @Query("SELECT * FROM Recipe")
        fun getAllRecipes(): LiveData<MutableList<Recipe>>
//
//        @Query("SELECT * FROM Student WHERE id = :id")
//        fun getStudentById(id: String): Recipe?
//
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertRecipes(vararg recipes: Recipe)
//
//        @Delete
//        fun deleteStudent(student: Recipe)
}