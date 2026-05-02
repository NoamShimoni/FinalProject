package com.finalProject.plateful.features.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalProject.plateful.data.repositories.auth.AuthRepository
import com.finalProject.plateful.databinding.FragmentEditProfileBinding
import com.finalProject.plateful.utils.extentions.loadAvatar

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null

    private val authRepository = AuthRepository.Companion.shared

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        val user = authRepository.getCurrentUser()

        user?.let {
            binding?.usernameTextInput?.setText(it.displayName)
            it.photoUrl?.let { imgUrl ->
                binding?.avatarImageView?.loadAvatar(imgUrl)
            }
        }

        return binding?.root
    }
}