package com.finalProject.plateful.data.models

import android.util.Log
import com.finalProject.plateful.base.BooleanCompletion
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class FirebaseAuthModel {
    private val auth = Firebase.auth

    fun signIn(email: String, password: String, completion: BooleanCompletion) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    completion(true)
                }
            }.addOnFailureListener {
                Log.e("TAG", "Sign in failed: ${it.message}")
                completion(false)
            }
    }

    fun signUp(email: String, password: String, completion: BooleanCompletion) {
        if (auth.currentUser !== null) { completion(false); return }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion(true)
            } else {
                completion(false)
            }
        }.addOnFailureListener {
            Log.v("TAG", "signIn failed: ${it.message}")
            completion(false)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun updateProfile(profileUpdates: UserProfileChangeRequest, completion: BooleanCompletion) {
        auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
            completion(true)
        }?.addOnFailureListener {
            Log.i("TAG", "Update profile failed: ${it.message}")
            completion(false)
        }
    }
}
