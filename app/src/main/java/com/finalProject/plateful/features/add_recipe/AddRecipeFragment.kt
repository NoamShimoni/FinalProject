package com.finalProject.plateful.features.add_recipe

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
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding
import com.finalProject.plateful.models.Recipe
import com.finalProject.plateful.utils.extentions.bitmap

class AddRecipeFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding? = null
    private val viewModel: AddRecipeViewModel by viewModels()
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
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

        binding?.saveRecipeButton?.setOnClickListener {
            binding?.loadingIndicator?.visibility = View.VISIBLE

            val recipeTitle = binding?.recipeTitleTextInput?.text.toString()
            val recipeIngredients = binding?.recipeIngredientsTextInput?.text.toString()
            val recipeInstructions = binding?.recipeInstructionsTextInput?.text.toString()
            val user = viewModel.getCurrentUser()

            user?.let { creatingUser ->
                val recipe = Recipe(
                    id = java.util.UUID.randomUUID().toString(),
                    title = recipeTitle,
                    ingredients = recipeIngredients,
                    instructions = recipeInstructions,
                    imageUrl = "",
                    creatingUserId = creatingUser.uid,
                    creatingUserName = creatingUser.displayName ?: "",
                    isDeleted = false,
                    lastUpdated = null
                )

                binding?.recipeImageImageView?.isDrawingCacheEnabled = true
                binding?.recipeImageImageView?.buildDrawingCache()

                val bitmap = binding?.recipeImageImageView?.bitmap

                bitmap?.let {
                    viewModel.addRecipe( it, recipe) {
                        dismiss()
                    }
                } ?: run {
                    Toast.makeText(context, "Please capture a profile image", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Log.v("TAG", "Error adding recipe. No current user")
            }
        }
    }


    private fun dismiss() {
        view?.findNavController()?.popBackStack()
    }
}