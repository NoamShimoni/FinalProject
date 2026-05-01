package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.BooleanCompletion
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.base.RecipesCompletion
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class FirebaseModel {
    val db = Firebase.firestore

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

    fun deleteRecipe(recipe: Recipe, completion: Completion) {
        db.collection(RECIPES)
            .document(recipe.id).update("isDeleted", true)
            .addOnSuccessListener { documentReference ->
                completion()
            }
            .addOnFailureListener { e ->
                completion()
            }
    }

    fun updateUserNameForRecipes(userId: String, newUserName: String, completion: BooleanCompletion) {
        db.collection(RECIPES).whereEqualTo("creatingUserId", userId).get()
            .addOnSuccessListener { querySnapshot ->
                db.runBatch { batch ->
                    for (doc in querySnapshot.documents) {
                        batch.update(doc.reference, "creatingUserName", newUserName, Recipe.Companion.LAST_UPDATED_KEY, FieldValue.serverTimestamp())
                    }
                }.addOnSuccessListener {
                    completion(true)
                }.addOnFailureListener {
                    completion(false)
                }
            }.addOnFailureListener {
                completion(false)
            }
    }
}
