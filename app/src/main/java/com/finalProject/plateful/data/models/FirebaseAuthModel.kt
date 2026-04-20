package com.finalProject.plateful.data.models

import android.util.Log
import com.finalProject.plateful.base.Completion
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class FirebaseAuthModel {
    private val auth = Firebase.auth

    fun signIn(email: String, password: String, completion: Completion) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    completion()
                }
            }.addOnFailureListener {
                Log.i("TAG", "Sign in failed: ${it.message}")
            }
    }

    fun signUp(email: String, password: String, completion: Completion) {
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

    fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
