package com.finalProject.plateful.utils

import android.util.Patterns
import com.finalProject.plateful.databinding.FragmentSignInBinding

object SignInFormValidator {
    fun validateForm(binding: FragmentSignInBinding): Boolean {
        var isValid = true

        val email = binding.emailTextInputLayout.editText?.text.toString().trim()
        val password = binding.passwordTextInputLayout.editText?.text.toString()

        if (email.isEmpty()) {
            binding.emailTextInputLayout.error = "Please enter an email address"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextInputLayout.error = "Please enter a valid email address"
            isValid = false
        } else {
            binding.emailTextInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordTextInputLayout.error = "Please enter a password"
            isValid = false
        } else {
            binding.passwordTextInputLayout.error = null
        }

        return isValid
    }
}
