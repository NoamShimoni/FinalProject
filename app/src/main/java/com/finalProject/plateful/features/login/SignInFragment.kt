package com.finalProject.plateful.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding?.loadingIndicator?.visibility = View.GONE

        binding?.signInButton?.setOnClickListener {
            val email = binding?.emailTextInputLayout?.editText?.text.toString()
            val password = binding?.passwordTextInputLayout?.editText?.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(context, "Please enter an email address", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.isEmpty()) {
                Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
            } else {
                binding?.loadingIndicator?.visibility = View.VISIBLE

                AuthRepository.shared.signIn(email, password) { isSuccess ->
                    isSuccess?.let { isSuccess ->
                        if (isSuccess) {
                            val action =
                                SignInFragmentDirections.actionSignInFragmentToRecipeListFragment()
                            it.findNavController().navigate(action)
                        }
                    }

                    binding?.loadingIndicator?.visibility = View.GONE
                }
            }
        }

        binding?.signUpTextView?.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            it.findNavController().navigate(action)
        }

        return binding?.root
    }
}