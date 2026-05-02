package com.finalProject.plateful.features.sign_in

import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository


class SignInViewModel: ViewModel() {
    fun signIn(email: String, password: String, completion: StringCompletion) {
        AuthRepository.shared.signIn(email, password) { error ->
            completion(error)
        }
    }
}