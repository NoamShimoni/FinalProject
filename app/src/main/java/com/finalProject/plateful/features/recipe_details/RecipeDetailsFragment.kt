package com.finalProject.plateful.features.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalProject.plateful.databinding.FragmentRecipeDetailsBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class RecipeDetailsFragment : Fragment() {
    private var binding: FragmentRecipeDetailsBinding? = null

    private var recipeImageView: ShapeableImageView? = null

    var title: String? = null
    var ingredients: String? = null
    var instructions: String? = null
    var imageUrl: String? = null
    var creatorName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE)
            ingredients = it.getString(INGREDIENTS)
            instructions = it.getString(INSTRUCTIONS)
            imageUrl = it.getString(IMAGE_URL)
            creatorName = it.getString(CREATOR_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        binding?.recipeTitleTextView?.text = title ?: "oops! title was not set"

        binding?.recipeCreatorTextView?.text = creatorName

        binding?.recipeIngredientsTextView?.text = ingredients ?: "oops! ingredients were not set"

        binding?.recipeInstructionsTextView?.text = instructions ?: "oops! instructions were not set"

        recipeImageView = binding?.recipeImageView
        Picasso.get().load(imageUrl).into(recipeImageView)

        return binding?.root
    }

    companion object {
        private const val TITLE: String = "TITLE_KEY"
        private const val INGREDIENTS: String = "INGREDIENTS_KEY"
        private const val INSTRUCTIONS: String = "INSTRUCTIONS_KEY"
        private const val IMAGE_URL: String = "IMAGE_URL_KEY"
        private const val CREATOR_NAME: String = "CREATOR_NAME_KEY"
    }
}