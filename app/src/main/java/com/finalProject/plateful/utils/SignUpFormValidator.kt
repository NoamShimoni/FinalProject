package com.finalProject.plateful.utils

import android.util.Patterns
import com.finalProject.plateful.databinding.FragmentSignUpBinding

object SignUpFormValidator {
    fun validateForm(binding: FragmentSignUpBinding): Boolean {
        var isValid = true

        val username = binding.usernameTextInputLayout.editText?.text.toString().trim()
        val email = binding.emailTextInputLayout.editText?.text.toString().trim()
        val password = binding.passwordTextInputLayout.editText?.text.toString()
        val confirmPassword = binding.confirmPasswordTextInputLayout.editText?.text.toString()

        if (username.isEmpty()) {
            binding.usernameTextInputLayout.error = "Please enter a username"
            isValid = false
        } else {
            binding.usernameTextInputLayout.error = null
        }

        if (email.isEmpty()) {
            binding.emailTextInputLayout.error = "Please enter an email address"
            isValid = false
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTextInputLayout.error = "Please enter a valid email address"
            isValid = false
        } else {
            binding.emailTextInputLayout.error = null
        }

        if (password.isEmpty() || password.length < 6) {
            binding.passwordTextInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.passwordTextInputLayout.error = null
        }

        if (confirmPassword != password) {
            binding.confirmPasswordTextInputLayout.error = "Password confirmation does not match"
            isValid = false
        } else {
            binding.confirmPasswordTextInputLayout.error = null
        }

        return isValid
    }
}
