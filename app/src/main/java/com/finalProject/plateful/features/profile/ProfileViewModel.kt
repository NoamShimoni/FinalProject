package com.finalProject.plateful.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.google.firebase.auth.FirebaseUser


class ProfileViewModel: ViewModel() {
    var currentUserRecipeCount: LiveData<Int> = RecipesRepository.shared.getAllRecipes(AuthRepository.shared.getCurrentUser()?.uid).map { it.size }

    fun refreshRecipes() {
        RecipesRepository.shared.refreshRecipes(null)
    }

    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }

    fun signOut() {
        AuthRepository.shared.signOut()
    }
}