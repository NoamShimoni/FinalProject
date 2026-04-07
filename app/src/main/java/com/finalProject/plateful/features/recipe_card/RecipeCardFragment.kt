package com.finalProject.plateful.features.recipe_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.finalProject.plateful.databinding.FragmentRecipeCardBinding
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class RecipeCardFragment : Fragment() {
    private var binding: FragmentRecipeCardBinding? = null
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
        binding = FragmentRecipeCardBinding.inflate(inflater, container, false)

        titleTextView = binding?.recipeTitleTextView
        titleTextView?.text = title ?: "oops! title was not set"

        ingredientsContentTextView = binding?.recipeIngredientsContentTextView
        ingredientsContentTextView?.text = ingredients ?: "oops! ingredients were not set"

        instructionsContentTextView = binding?.recipeInstructionsContentTextView
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