package com.finalProject.plateful.features.recipe_details

import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser


class RecipeDetailsViewModel: ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }
}