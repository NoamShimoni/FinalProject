package com.finalProject.plateful.data.repositories.auth

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.BooleanCompletion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseAuthModel
import com.finalProject.plateful.data.models.FirebaseModel
import com.finalProject.plateful.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class AuthRepository private constructor() {
    private val storageModel: CloudinaryStorageModel = CloudinaryStorageModel.shared
    private val firebaseAuthModel: FirebaseAuthModel = FirebaseAuthModel()
    private val firebaseModel: FirebaseModel = FirebaseModel()

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
        firebaseAuthModel.signUp(email, password) {
            val user = Firebase.auth.currentUser
            if (user == null) {
                completion(false)
            } else {
                if (profileImageBitmap != null) {
                    storageModel.uploadProfileImage(profileImageBitmap, user.uid) { imageUrl ->
                        val profileUpdates = UserProfileChangeRequest.Builder()

                        profileUpdates.displayName = username

                        if (imageUrl != null) {
                            profileUpdates.photoUri = Uri.parse(imageUrl)
                        }

                        this.updateProfile(profileUpdates.build(), User(
                            user.uid,
                            username,
                            null
                        ), completion)
                    }
                } else {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    this.updateProfile(profileUpdates, User(
                        user.uid,
                        username,
                        null
                    ), completion)
                }
            }
        }
    }

    fun signOut() {
        firebaseAuthModel.signOut()
    }

    fun isUserSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }

    private fun updateProfile(
        profileUpdates: UserProfileChangeRequest,
        user: User,
        completion: BooleanCompletion
    ): Unit {
        firebaseAuthModel.updateProfile(profileUpdates) {
            firebaseModel.addUser(user, completion)
        }
    }
}
