package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

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

    fun signInWithEmailAndPassword(email: String, password: String, completion: Completion) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion()
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, completion: Completion) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion()
            }
        }
    }

    fun updateProfile(profileUpdates: UserProfileChangeRequest, completion: Completion) {
        auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion()
            }
        } ?: completion()
    }
}
