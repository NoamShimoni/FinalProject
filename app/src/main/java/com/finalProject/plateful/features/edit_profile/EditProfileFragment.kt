package com.finalProject.plateful.features.edit_profile

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentEditProfileBinding
import com.finalProject.plateful.utils.extentions.bitmap
import com.finalProject.plateful.utils.extentions.loadAvatar
import com.google.firebase.auth.FirebaseUser
import kotlin.getValue

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private val viewModel: EditProfileViewModel by viewModels()

    private var user: FirebaseUser? = null

    private var isImageSelected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        user = viewModel.getCurrentUser()

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                bitMap ->
            bitMap?.let {
                binding?.avatarImageView?.setImageBitmap(it)
                isImageSelected = true
            } ?: Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }

        binding?.editAvatarButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        setUserInfo()

        binding?.loadingIndicator?.visibility = View.GONE

        binding?.saveButton?.setOnClickListener {
            val newName = binding?.usernameTextInput?.text.toString()

                if (newName != user?.displayName || isImageSelected) {
                handleProfileSaving()
            } else {
                Toast.makeText(context, "No changes to save", Toast.LENGTH_SHORT).show()
            }
        }

        return binding?.root
    }

    fun setUserInfo() {
        user?.let {
            binding?.usernameTextInput?.setText(it.displayName)

            it.photoUrl?.let { imgUrl ->
                binding?.avatarImageView?.loadAvatar(imgUrl)
            }
        }
    }

    fun handleProfileSaving() {
        binding?.loadingIndicator?.visibility = View.VISIBLE

        var bitmap: Bitmap? = null
        if (isImageSelected) {
            binding?.avatarImageView?.isDrawingCacheEnabled = true
            binding?.avatarImageView?.buildDrawingCache()
            bitmap = binding?.avatarImageView?.bitmap
        }

        val newName = binding?.usernameTextInput?.text.toString()

        viewModel.updateProfile(newName, bitmap) { isSuccess ->
            if (isSuccess) {
                view?.findNavController()?.popBackStack()
            } else {
                Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }

            binding?.loadingIndicator?.visibility = View.GONE
        }
    }
}