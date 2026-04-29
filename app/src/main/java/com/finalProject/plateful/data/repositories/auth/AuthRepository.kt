package com.finalProject.plateful.data.repositories.auth

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.BooleanCompletion
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

    fun signIn(email: String, password: String, completion: BooleanCompletion) {
        firebaseAuthModel.signIn(email, password, completion)
    }

    fun signUp(
        username: String,
        email: String,
        password: String,
        profileImageBitmap: Bitmap?,
        completion: BooleanCompletion
    ) {
        firebaseAuthModel.signUp(email, password) { isSuccess ->
            val user = Firebase.auth.currentUser

            if (user == null || !isSuccess) {
                completion(false)
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
    }

    fun signOut() {
        firebaseAuthModel.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    fun isUserSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }
}
