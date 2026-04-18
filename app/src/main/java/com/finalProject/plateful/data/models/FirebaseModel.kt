package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirebaseModel {
    val db = Firebase.firestore

    private companion object {
        const val RECIPES = "recipes"
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
}
