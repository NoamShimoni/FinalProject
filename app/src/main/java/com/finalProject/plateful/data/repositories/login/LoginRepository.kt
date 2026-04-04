package com.finalProject.plateful.data.repositories.login

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseModel
import com.google.firebase.auth.UserProfileChangeRequest

class LoginRepository private constructor() {

    private val storageModel = CloudinaryStorageModel()
    private val firebaseModel = FirebaseModel()

    companion object {
        val shared = LoginRepository()
    }

    fun login(email: String, password: String, completion: Completion) {
        firebaseModel.signInWithEmailAndPassword(email, password, completion)
    }
    
    fun register(
        username: String,
        email: String,
        password: String,
        profileImageBitmap: Bitmap?,
        completion: Completion
    ) {
        firebaseModel.createUserWithEmailAndPassword(email, password) {
            val user = firebaseModel.auth.currentUser
            if (user != null) {
                if (profileImageBitmap != null) {
                    storageModel.uploadProfileImage(profileImageBitmap, user.uid) { imageUrl ->
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)

                        if (imageUrl != null) {
                            profileUpdates.setPhotoUri(Uri.parse(imageUrl))
                        }

                        firebaseModel.updateProfile(profileUpdates.build(), completion)
                    }
                } else {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    firebaseModel.updateProfile(profileUpdates, completion)
                }
            } else {
                completion()
            }
        }
    }
}
