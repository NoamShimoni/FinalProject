package com.finalProject.plateful.base

import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository


class MainActivityViewModel: ViewModel() {
    fun isUserSignedIn(): Boolean {
        return AuthRepository.shared.isUserSignedIn()
    }
}