package com.finalProject.plateful.features.sign_up

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository


class SignUpViewModel: ViewModel() {
    fun signUp(username: String, email: String, password: String, bitmap: Bitmap?, completion: StringCompletion) {
        AuthRepository.Companion.shared.signUp(username, email, password, bitmap,completion)
    }
}