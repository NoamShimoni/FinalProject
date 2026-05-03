package com.finalProject.plateful.features.sign_up

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentSignUpBinding
import com.finalProject.plateful.utils.SignUpFormValidator
import com.finalProject.plateful.utils.extentions.bitmap
import com.finalProject.plateful.utils.extentions.setAvatarImageBitmap

class SignUpFragment : Fragment() {
    private var binding: FragmentSignUpBinding? = null
    private val viewModel: SignUpViewModel by viewModels()

    private var isImageSelected = false

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitMap ->
            bitMap?.let {
                binding?.profilePreviewImageView?.setAvatarImageBitmap(it)
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
            performRegistration(it)
        }

        return binding?.root
    }

    private fun performRegistration(it: View) {
        binding?.let { binding ->
            if (SignUpFormValidator.validateForm(binding)) {
                val username = binding.usernameTextInputLayout.editText?.text.toString().trim()
                val email = binding.emailTextInputLayout.editText?.text.toString().trim()
                val password = binding.passwordTextInputLayout.editText?.text.toString()

                binding.loadingIndicator.visibility = View.VISIBLE

                var bitmap: Bitmap? = null
                if (isImageSelected) {
                    binding.profilePreviewImageView.isDrawingCacheEnabled = true
                    binding.profilePreviewImageView.buildDrawingCache()
                    bitmap = binding.profilePreviewImageView.bitmap
                }

                viewModel.signUp(username, email, password, bitmap) { error ->
                    error?.let { errorMsg ->
                        binding.loadingIndicator.visibility = View.GONE
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        val action = SignUpFragmentDirections.actionSignUpFragmentToRecipeListFragment()
                        it.findNavController().navigate(action)
                    }
                }
            }
        }
    }
}