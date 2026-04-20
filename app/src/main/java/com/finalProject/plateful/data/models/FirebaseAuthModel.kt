package com.finalProject.plateful.data.models

import android.util.Log
import com.finalProject.plateful.base.Completion
import com.google.firebase.Firebase
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
}
