package com.finalProject.plateful.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding?.editProfileButton?.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment("Noam", "sd")
            it.findNavController().navigate(action)
        }

        return binding?.root
    }
}