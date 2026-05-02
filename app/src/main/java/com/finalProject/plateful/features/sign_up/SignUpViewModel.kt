package com.finalProject.plateful.features.sign_up

import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.findNavController
import com.finalProject.plateful.base.Completion
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.features.sign_in.SignUpFragmentDirections
import com.google.firebase.auth.FirebaseUser


class SignUpViewModel: ViewModel() {
    fun signUp(username: String, email: String, password: String, bitmap: Bitmap?, completion: StringCompletion) {
        AuthRepository.Companion.shared.signUp(username, email, password, bitmap) { error ->
            completion(error)
        }
    }
}