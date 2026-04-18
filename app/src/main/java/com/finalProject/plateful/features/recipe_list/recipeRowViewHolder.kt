package com.finalProject.plateful.features.recipe_list

import androidx.recyclerview.widget.RecyclerView
import com.finalProject.plateful.databinding.RecipeRowLayoutBinding
import com.finalProject.plateful.models.Recipe
import com.squareup.picasso.Picasso

class RecipeRowViewHolder(
    private val binding: RecipeRowLayoutBinding,
): RecyclerView.ViewHolder(binding.root) {
    private var recipe: Recipe? = null

    fun bind(recipe: Recipe, position: Int) {
        this.recipe = recipe

        binding.recipeTitleTextView.text = recipe.title

        Picasso.get().load(recipe.imageUrl).into(binding.recipeImageView)
    }

}