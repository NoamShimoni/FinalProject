package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.auth.FirebaseAuth

class FirebaseModel {
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()

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

    fun signInWithEmailAndPassword(email: String, password: String, completion: Completion) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion()
            }
        }
    }
}
