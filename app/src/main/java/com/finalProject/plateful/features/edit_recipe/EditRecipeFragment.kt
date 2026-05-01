package com.finalProject.plateful.features.edit_recipe

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding
import com.finalProject.plateful.models.Recipe
import com.finalProject.plateful.utils.extentions.bitmap
import com.squareup.picasso.Picasso

class EditRecipeFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding? = null
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var hasImageChanged = false

    var id: String? = null
    var title: String? = null
    var ingredients: String? = null
    var instructions: String? = null
    var imageUrl: String? = null
    var creatingUserId: String? = null
    var creatingUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.id = it.getString(Recipe.ID_KEY)
            this.title = it.getString(Recipe.TITLE_KEY)
            this.ingredients = it.getString(Recipe.INGREDIENTS_KEY)
            this.instructions = it.getString(Recipe.INSTRUCTIONS_KEY)
            this.imageUrl = it.getString(Recipe.IMAGE_URL_KEY)
            this.creatingUserId = it.getString(Recipe.CREATING_USER_ID_KEY)
            this.creatingUserName = it.getString(Recipe.CREATING_USER_NAME_KEY)

            if (this.id.isNullOrEmpty() || this.creatingUserId.isNullOrEmpty()) {
                this.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        setupView()

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitMap ->
                bitMap?.let {
                    binding?.recipeImageImageView?.setImageBitmap(it)
                    this.hasImageChanged = true
                } ?: Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show()
            }

        binding?.takePictureButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        return binding?.root
    }

    private fun setupView() {
        binding?.loadingIndicator?.visibility = View.GONE

        binding?.recipeTitleTextInput?.setText(title)
        binding?.recipeIngredientsTextInput?.setText(ingredients)
        binding?.recipeInstructionsTextInput?.setText(instructions)

        if (!imageUrl.isNullOrEmpty()) {
            binding?.recipeImageImageView?.let { imageView ->
                Picasso.get().load(imageUrl).into(imageView)
            }
        }

        binding?.saveRecipeButton?.setOnClickListener {
            binding?.loadingIndicator?.visibility = View.VISIBLE

            val recipeTitle = binding?.recipeTitleTextInput?.text.toString()
            val recipeIngredients = binding?.recipeIngredientsTextInput?.text.toString()
            val recipeInstructions = binding?.recipeInstructionsTextInput?.text.toString()

            id?.let { id ->
                creatingUserId?.let { creatingUserId ->
                    var imageBitmap: Bitmap? = null

                    if (this.hasImageChanged) {
                        binding?.recipeImageImageView?.isDrawingCacheEnabled = true
                        binding?.recipeImageImageView?.buildDrawingCache()

                        imageBitmap = binding?.recipeImageImageView?.bitmap
                    }

                    val recipe = Recipe(
                        id = id,
                        title = recipeTitle,
                        ingredients = recipeIngredients,
                        instructions = recipeInstructions,
                        imageUrl = imageUrl ?: "",
                        creatingUserId = creatingUserId,
                        creatingUserName = creatingUserName ?: "",
                        isDeleted = false,
                        lastUpdated = Recipe.lastUpdated
                    )

                    RecipesRepository.shared.editRecipe(recipe, imageBitmap) {
                        dismiss()
                    }
                }
            }
        }
    }


    private fun dismiss() {
        view?.findNavController()?.popBackStack()
    }
}