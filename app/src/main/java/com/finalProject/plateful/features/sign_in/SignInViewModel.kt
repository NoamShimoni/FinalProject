package com.finalProject.plateful.features.sign_in

import androidx.lifecycle.ViewModel
import com.finalProject.plateful.base.StringCompletion
import com.finalProject.plateful.data.repositories.auth.AuthRepository


class SignInViewModel: ViewModel() {
    fun signIn(email: String, password: String, completion: StringCompletion) {
        AuthRepository.shared.signIn(email, password, completion)
    }
}