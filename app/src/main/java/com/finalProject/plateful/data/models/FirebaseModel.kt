package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.BooleanCompletion
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.base.RecipesCompletion
import com.finalProject.plateful.models.Recipe
import com.finalProject.plateful.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirebaseModel {
    val db = Firebase.firestore

    private companion object {
        const val RECIPES = "recipes"
        const val USERS = "users"
    }

    fun getAllRecipes(completion: RecipesCompletion) {
        db.collection(RECIPES).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> completion(it.result.map { Recipe.Companion.fromJson(it.data) })
                false -> completion(emptyList())
            }
        }
    }

    fun addRecipe(recipe: Recipe, completion: Completion) {
        db.collection(RECIPES)
            .document(recipe.id)
            .set(recipe.toJson)
            .addOnSuccessListener { documentReference ->
                completion()
            }
            .addOnFailureListener { e ->
                completion()
            }
    }

    fun deleteRecipe(recipe: Recipe, completion: Completion) {
        db.collection(RECIPES)
            .document(recipe.id).delete()
            .addOnSuccessListener { documentReference ->
                completion()
            }
            .addOnFailureListener { e ->
                completion()
            }
    }

    fun getAllUsers(completion: RecipesCompletion) {
        db.collection(USERS).get().addOnCompleteListener {
            when (it.isSuccessful) {
                true -> completion(it.result.map { Recipe.Companion.fromJson(it.data) })
                false -> completion(emptyList())
            }
        }
    }

    fun addUser(user: User, completion: BooleanCompletion) {
        db.collection(USERS)
            .document(user.id)
            .set(user.toJson)
            .addOnSuccessListener { documentReference ->
                completion(true)
            }
            .addOnFailureListener { e ->
                completion(false)
            }
    }
}
