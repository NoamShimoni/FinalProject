package com.finalProject.plateful.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalProject.plateful.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null

    var username: String? = null
    var avatarUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
            avatarUrl = it.getString(AVATAR_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    companion object {
        private const val USERNAME: String = "USERNAME_KEY"
        private const val AVATAR_URL: String = "AVATAR_URL_KEY"
    }
}