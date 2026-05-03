package com.finalProject.plateful.features.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentSignInBinding
import com.finalProject.plateful.utils.SignInFormValidator

class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding?.loadingIndicator?.visibility = View.GONE

        binding?.signInButton?.setOnClickListener {
            binding?.let { binding ->
                if (!SignInFormValidator.validateForm(binding)) {
                    val email = binding.emailTextInputLayout.editText?.text.toString().trim()
                    val password = binding.passwordTextInputLayout.editText?.text.toString()

                    binding.loadingIndicator.visibility = View.VISIBLE

                    viewModel.signIn(email, password) { error ->
                        error?.let { errorMsg ->
                            binding.loadingIndicator.visibility = View.GONE
                            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                        } ?: run {
                            val action =
                                SignInFragmentDirections.actionSignInFragmentToRecipeListFragment()
                            view?.findNavController()?.navigate(action)
                        }
                    }
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