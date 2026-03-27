package com.finalProject.plateful.features.add_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalProject.plateful.R
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding

class AddRecipeFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(layoutInflater, container, false)

        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }


}