package com.finalProject.plateful.features.edit_profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.google.firebase.auth.FirebaseUser

class EditProfileViewModel: ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }

    fun updateProfile(name: String, profileImageBitmap: Bitmap?, completion: StringCompletion) {
        AuthRepository.shared.updateProfile(name, profileImageBitmap) { error ->
            if (error.isNullOrEmpty()) {
                val user = getCurrentUser()
                if (user != null) {
                    RecipesRepository.shared.updateUserNameForRecipes(user.uid, name, completion)
                } else {
                    completion("User is not signed in")
                }
            } else {
                completion(error)
            }
        }
    }
}