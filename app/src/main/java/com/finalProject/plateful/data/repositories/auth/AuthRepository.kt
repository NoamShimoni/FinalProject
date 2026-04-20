package com.finalProject.plateful.data.repositories.auth

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseAuthModel
import com.google.firebase.auth.UserProfileChangeRequest

class AuthRepository private constructor() {
    private val storageModel = CloudinaryStorageModel()
    private val firebaseAuthModel = FirebaseAuthModel()

    companion object {
        val shared = AuthRepository()
    }

    fun signIn(email: String, password: String, completion: Completion) {
        firebaseAuthModel.signIn(email, password, completion)
    }

    fun register(
        username: String,
        email: String,
        password: String,
        profileImageBitmap: Bitmap?,
        completion: Completion
    ) {
        firebaseAuthModel.signUp(email, password) {
            val user = firebaseAuthModel.currentUser()
            if (user != null) {
                if (profileImageBitmap != null) {
                    storageModel.uploadProfileImage(profileImageBitmap, user.uid) { imageUrl ->
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)

                        if (imageUrl != null) {
                            profileUpdates.setPhotoUri(Uri.parse(imageUrl))
                        }

                        firebaseAuthModel.updateProfile(profileUpdates.build(), completion)
                    }
                } else {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    firebaseAuthModel.updateProfile(profileUpdates, completion)
                }
            } else {
                completion()
            }
        }
    }

    fun isUserSignedIn(): Boolean {
        return firebaseAuthModel.currentUser() != null
    }
}
