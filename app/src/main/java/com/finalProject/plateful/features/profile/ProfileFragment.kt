package com.finalProject.plateful.features.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.finalProject.plateful.R
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding
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

        return binding?.root
    }
}