package com.finalProject.plateful.features.add_recipe

import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AddRecipeViewModel: ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }
}