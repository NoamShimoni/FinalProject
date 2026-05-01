package com.finalProject.plateful.features.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.finalProject.plateful.databinding.FragmentRecipeDetailsBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class RecipeDetailsFragment : Fragment() {
    private var binding: FragmentRecipeDetailsBinding? = null
    private var titleTextView: TextView? = null
    private var ingredientsContentTextView: TextView? = null
    private var instructionsContentTextView: TextView? = null

    private var recipeImageView: ShapeableImageView? = null

    var title: String? = null
    var ingredients: String? = null
    var instructions: String? = null
    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(TITLE)
            ingredients = it.getString(INGREDIENTS)
            instructions = it.getString(INSTRUCTIONS)
            imageUrl = it.getString(IMAGE_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        titleTextView = binding?.recipeTitleTextView
        titleTextView?.text = title ?: "oops! title was not set"

        ingredientsContentTextView = binding?.recipeIngredientsTextView
        ingredientsContentTextView?.text = ingredients ?: "oops! ingredients were not set"

        instructionsContentTextView = binding?.recipeInstructionsTextView
        instructionsContentTextView?.text = instructions ?: "oops! instructions were not set"

        recipeImageView = binding?.recipeImageView
        Picasso.get().load(imageUrl).into(recipeImageView)

        return binding?.root
    }

    companion object {
        private const val TITLE: String = "TITLE_KEY"
        private const val INGREDIENTS: String = "INGREDIENTS_KEY"
        private const val INSTRUCTIONS: String = "INSTRUCTIONS_KEY"
        private const val IMAGE_URL: String = "IMAGE_URL_KEY"
    }
}