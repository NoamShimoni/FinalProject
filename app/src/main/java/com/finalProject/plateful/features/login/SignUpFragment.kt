package com.finalProject.plateful.features.login

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.databinding.FragmentSignUpBinding
import com.finalProject.plateful.utils.extentions.bitmap

class SignUpFragment : Fragment() {
    private var binding: FragmentSignUpBinding? = null

    private var isImageSelected = false

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitMap ->
            bitMap?.let {
                binding?.profilePreviewImageView?.setImageBitmap(it)
                isImageSelected = true
            } ?: Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding?.loadingIndicator?.visibility = View.GONE

        binding?.uploadPhotoButton?.setOnClickListener {
            cameraLauncher.launch(null)
        }

        binding?.createAccountButton?.setOnClickListener {
            if(performRegistration()) {
                val action = SignUpFragmentDirections.actionSignInFragmentToRecipeListFragment()
                it.findNavController().navigate(action)
            } else {
                binding?.loadingIndicator?.visibility = View.GONE
            }
        }

        return binding?.root
    }

    private fun performRegistration(): Boolean {
        val username = binding?.usernameTextInputLayout?.editText?.text.toString().trim()
        val email = binding?.emailTextInputLayout?.editText?.text.toString().trim()
        val password = binding?.passwordTextInputLayout?.editText?.text.toString()
        val confirmPassword = binding?.confirmPasswordTextInputLayout?.editText?.text.toString()

        var isSuccess = false

        if (username.isEmpty()) {
            Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty() || password.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Password confirmation does not match", Toast.LENGTH_SHORT)
                .show()
        } else {
            binding?.loadingIndicator?.visibility = View.VISIBLE

            var bitmap: Bitmap? = null
            if (isImageSelected) {
                binding?.profilePreviewImageView?.isDrawingCacheEnabled = true
                binding?.profilePreviewImageView?.buildDrawingCache()
                bitmap = binding?.profilePreviewImageView?.bitmap
            }

            AuthRepository.shared.signUp(username, email, password, bitmap) {
                isSuccess = it ?: false
            }
        }

        return isSuccess
    }
}
