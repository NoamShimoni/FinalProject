package com.finalProject.plateful.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalProject.plateful.databinding.FragmentRegisterBinding
import com.finalProject.plateful.utils.extentions.bitmap
import com.finalProject.plateful.data.repositories.login.LoginRepository
import android.graphics.Bitmap
import com.finalProject.plateful.R

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private var isImageSelected = false

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitMap ->
            bitMap?.let {
                binding.ivProfilePreview.setImageBitmap(it)
                isImageSelected = true
            } ?: Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnUploadPhoto.setOnClickListener {
            cameraLauncher.launch(null)
        }

        binding.btnCreateAccount.setOnClickListener {
            performRegistration()
        }
    }

    private fun performRegistration() {
        val username = binding.tilUsername.editText?.text.toString().trim()
        val email = binding.tilEmail.editText?.text.toString().trim()
        val password = binding.tilPassword.editText?.text.toString()
        val confirmPassword = binding.tilConfirmPassword.editText?.text.toString()

        if (username.isEmpty()) {
            Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
            return
        } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        } else if (password.isEmpty() || password.length < 6) {
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                .show()
            return
        } else if (password != confirmPassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        binding.loadingIndicator.visibility = View.VISIBLE
        binding.btnCreateAccount.isEnabled = false

        var bitmap: Bitmap? = null
        if (isImageSelected) {
            binding.ivProfilePreview.isDrawingCacheEnabled = true
            binding.ivProfilePreview.buildDrawingCache()
            bitmap = binding.ivProfilePreview.bitmap
        }

        LoginRepository.shared.register(username, email, password, bitmap) {
            finishRegistration()
        }
    }

    private fun finishRegistration() {
        binding.loadingIndicator.visibility = View.GONE
        binding.btnCreateAccount.isEnabled = true
        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginFragment_to_addRecipeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
