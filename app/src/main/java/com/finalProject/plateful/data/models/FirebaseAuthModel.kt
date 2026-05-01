package com.finalProject.plateful.data.models

import com.finalProject.plateful.base.StringCompletion
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class FirebaseAuthModel {
    private val auth = Firebase.auth

    fun signIn(email: String, password: String, completion: StringCompletion) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    completion(null)
                } else {
                    val errorMessage =
                        when ((task.exception as? FirebaseAuthException)?.errorCode) {
                            "ERROR_INVALID_CREDENTIAL" -> "Email or password are incorrect"
                            "ERROR_INVALID_EMAIL" -> "Please enter a valid email address"
                            "ERROR_USER_DISABLED" -> "This account has been disabled"
                            else -> "Error signing in. Please try again."
                        }
                    completion(errorMessage)
                }
            }
    }

    fun signUp(email: String, password: String, completion: StringCompletion) {
        if (auth.currentUser !== null) {
            completion("Error signing up. Try again later"); return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    completion(null)
                } else {
                    val errorMessage =
                        when ((task.exception as? FirebaseAuthException)?.errorCode) {
                            "ERROR_EMAIL_ALREADY_IN_USE" -> "This email is already registered"
                            "ERROR_INVALID_EMAIL" -> "Please enter a valid email address"
                            "ERROR_WEAK_PASSWORD" -> "Password is too weak. Use at least 6 characters"
                            else -> "Error creating account. Please try again."
                        }
                    completion(errorMessage)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    fun updateProfile(profileUpdates: UserProfileChangeRequest, completion: StringCompletion) {
        auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                completion(null)
            } else {
                val errorMessage = task.exception?.localizedMessage ?: "Failed to update profile"
                completion(errorMessage)
            }
        }
    }
}
