package com.finalProject.plateful.data.repositories.auth

import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.data.models.FirebaseAuthModel

class AuthRepository private constructor() {
    private val firebaseAuthModel = FirebaseAuthModel()

    companion object {
        val shared = AuthRepository()
    }

    fun signIn(email: String, password: String, completion: Completion) {
        firebaseAuthModel.signIn(email, password, completion)
    }
}