package com.finalProject.plateful.features.recipe_card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.card.MaterialCardView
import com.finalProject.plateful.databinding.ViewRecipeCardBinding
import com.finalProject.plateful.models.Recipe

class RecipeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {
    private val binding: ViewRecipeCardBinding

    init {
        binding = ViewRecipeCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun bind(recipe: Recipe) {
        binding.recipeTitle.text = recipe.title
        binding.btnEdit.tag = recipe.id
        binding.btnDelete.tag = recipe.id
        // TODO: ask noam about author and how to load image to image view

        this.setupClickListeners(recipe)
    }

    private fun setupClickListeners(recipe: Recipe) {
        binding.btnEdit.setOnClickListener {
            // TODO: Implement edit logic
        }

        binding.btnDelete.setOnClickListener {
            // TODO: Implement delete logic
        }
    }
}