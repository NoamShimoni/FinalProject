package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.base.RecipesCompletion
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.google.firebase.auth.FirebaseAuth

class FirebaseModel {
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

    private companion object {
        const val RECIPES = "recipes"
    }

    fun getAllRecipes(since: Long, completion: RecipesCompletion) {
        db.collection(RECIPES)
            .whereGreaterThanOrEqualTo(Recipe.Companion.LAST_UPDATED_KEY, Timestamp(since / 1000, 0)).get().addOnCompleteListener {
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

    fun signInWithEmailAndPassword(email: String, password: String, completion: Completion) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion()
            }
        }
    }
}
