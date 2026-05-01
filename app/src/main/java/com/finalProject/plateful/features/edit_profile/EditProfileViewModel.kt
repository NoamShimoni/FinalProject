package com.finalProject.plateful.features.edit_profile

import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.BooleanCompletion
import android.graphics.Bitmap
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser


class EditProfileViewModel: ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return AuthRepository.shared.getCurrentUser()
    }

    fun updateProfile(name: String, profileImageBitmap: Bitmap?, completion: BooleanCompletion) {
        AuthRepository.shared.updateProfile(name, profileImageBitmap,completion)
    }
}