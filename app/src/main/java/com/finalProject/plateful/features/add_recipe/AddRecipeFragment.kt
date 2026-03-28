package com.finalProject.plateful.features.add_recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import com.finalProject.plateful.utils.extentions.bitmap
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding
import com.finalProject.plateful.models.Recipe

class AddRecipeFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(layoutInflater, container, false)
        setupView()

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                bitMap ->
            bitMap?.let {
                binding?.recipeImageImageView?.setImageBitmap(it)
            } ?: Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
        }

        binding?.takePictureButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        return binding?.root
    }

    private fun setupView() {
        binding?.loadingIndicator?.visibility = View.GONE

        binding?.cancelButton?.setOnClickListener {
            dismiss()
        }

        binding?.saveButton?.setOnClickListener {
            binding?.loadingIndicator?.visibility = View.VISIBLE

            val recipeTitle = binding?.recipeTitleEditText?.text.toString()
            val recipeIngredients = binding?.recipeIngredientsEditText?.text.toString()
            val recipeInstructions = binding?.recipeInstructionsEditText?.text.toString()

            val recipe = Recipe(
                id = "1",
                title = recipeTitle,
                ingredients = recipeIngredients,
                instructions = recipeInstructions,
                imageUrl = "",
                lastUpdated = null
            )

            binding?.recipeImageImageView?.isDrawingCacheEnabled = true
            binding?.recipeImageImageView?.buildDrawingCache()

            val bitmap = binding?.recipeImageImageView?.bitmap

            bitmap?.let {
                RecipesRepository.shared.addRecipe( it, recipe, ) {
                    dismiss()
                }
            } ?: run {
                Toast.makeText(context, "Please capture a profile image", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun dismiss() {
        view?.findNavController()?.popBackStack()
    }
}