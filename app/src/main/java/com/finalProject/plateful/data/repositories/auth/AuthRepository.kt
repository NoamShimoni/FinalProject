package com.finalProject.plateful.data.repositories.auth

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseAuthModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class AuthRepository private constructor() {
    private val storageModel: CloudinaryStorageModel = CloudinaryStorageModel.shared
    private val firebaseAuthModel: FirebaseAuthModel = FirebaseAuthModel()

    companion object {
        val shared = AuthRepository()
    }

    fun signIn(email: String, password: String, completion: StringCompletion) {
        firebaseAuthModel.signIn(email, password, completion)
    }

    fun signUp(
        username: String,
        email: String,
        password: String,
        profileImageBitmap: Bitmap?,
        completion: StringCompletion
    ) {
        firebaseAuthModel.signUp(email, password) { error ->
            if (error.isNullOrEmpty()) {
                updateProfile(username, profileImageBitmap, completion)
            } else {
                completion(error)
            }
        }
    }

    fun updateProfile(username: String, profileImageBitmap: Bitmap?, completion: StringCompletion) {
        val user = Firebase.auth.currentUser

        if (user == null) {
            completion("User is not signed in")
        } else {
            if (profileImageBitmap != null) {
                storageModel.uploadProfileImage(profileImageBitmap, user.uid) { imageUrl ->
                    val profileUpdates = UserProfileChangeRequest.Builder()

                    profileUpdates.displayName = username

                    if (imageUrl != null) {
                        profileUpdates.photoUri = Uri.parse(imageUrl)
                    }

                    firebaseAuthModel.updateProfile(profileUpdates.build(), completion)
                }
            } else {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()

                firebaseAuthModel.updateProfile(profileUpdates, completion)
            }
        }
    }

    fun signOut() {
        firebaseAuthModel.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    fun isUserSignedIn(): Boolean {
        return this.getCurrentUser() != null
    }
}
