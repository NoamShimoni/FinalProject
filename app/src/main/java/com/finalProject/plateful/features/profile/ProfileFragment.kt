package com.finalProject.plateful.features.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentProfileBinding
import com.finalProject.plateful.utils.extentions.loadAvatar

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.currentUserRecipeCount.observe(viewLifecycleOwner) { count ->
            binding?.myRecipesCountTextView?.text = count.toString()
        }

        binding?.editProfileButton?.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            it.findNavController().navigate(action)
        }

        binding?.logoutNavigationCard?.setOnClickListener {
            viewModel.signOut()
            val action = ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
            it.findNavController().navigate(action)
        }

        setUserInfo()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshRecipes()
    }

    fun setUserInfo() {
        val user = viewModel.getCurrentUser()

        Log.v("ProfileFragment", "Current user: ${user?.displayName}, email: ${user?.email}, photoUrl: ${user?.photoUrl}")

        user?.let {
            binding?.usernameTextView?.text = it.displayName

            it.photoUrl?.let { imgUrl ->
                binding?.avatarImageView?.loadAvatar(imgUrl)
            }

        }
    }
}