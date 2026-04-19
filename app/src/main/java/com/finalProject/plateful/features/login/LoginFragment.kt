package com.finalProject.plateful.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalProject.plateful.R
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.loadingIndicator?.visibility = View.GONE

        binding?.signInButton?.setOnClickListener {
            val email = binding?.emailTextInputLayout?.editText?.text.toString()
            val password = binding?.passwordTextInputLayout?.editText?.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(context, "Please enter an email address", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
            } else {
                binding?.loadingIndicator?.visibility = View.VISIBLE

                AuthRepository.shared.signIn(email, password) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.bottom_navigation_menu_home)
                }
            }
        }

        binding?.signUpTextView?.setOnClickListener {
            //TODO: navigate to register screen
        }

        return binding?.root
    }
}