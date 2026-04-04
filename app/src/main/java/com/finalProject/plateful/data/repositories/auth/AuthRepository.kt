package com.finalProject.plateful.data.repositories.auth

import android.graphics.Bitmap
import android.net.Uri
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.models.CloudinaryStorageModel
import com.finalProject.plateful.data.models.FirebaseModel
class AuthRepository private constructor() {
    private val storageModel = CloudinaryStorageModel()
    private val firebaseModel = FirebaseModel()

    companion object {
        val shared = AuthRepository()
    }

    fun login(email: String, password: String, completion: Completion) {
        firebaseModel.signInWithEmailAndPassword(email, password, completion)
    }
}