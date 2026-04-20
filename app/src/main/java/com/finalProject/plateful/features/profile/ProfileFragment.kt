package com.finalProject.plateful.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.finalProject.plateful.databinding.FragmentProfileBinding
import com.finalProject.plateful.features.recipe_list.RecipesListViewModel
import kotlin.getValue

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

    private val viewModel: RecipesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            binding?.myRecipesCountTextView?.text = recipes?.size?.toString() ?: "0"
        }

        binding?.editProfileButton?.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment("Noam", "https://res.cloudinary.com/dltg3tc47/image/upload/v1775500141/avrrwceyrdnw40cnlmih.jpg")
            it.findNavController().navigate(action)
        }

        return binding?.root
    }
}