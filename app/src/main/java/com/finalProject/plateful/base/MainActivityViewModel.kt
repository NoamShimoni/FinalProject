package com.finalProject.plateful.base

import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.auth.AuthRepository


class MainActivityViewModel: ViewModel() {
    fun isUserSignedIn(): Boolean {
        return AuthRepository.shared.isUserSignedIn()
    }
}