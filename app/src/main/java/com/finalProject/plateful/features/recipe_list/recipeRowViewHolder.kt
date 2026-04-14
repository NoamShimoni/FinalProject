package com.finalProject.plateful.features.recipe_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
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

        val isOwner = false //TODO: implement this check when Recipe model has a creatingUserId

        if (isOwner) {
            binding.editDeleteDivider.visibility = View.VISIBLE
            binding.buttonsContainer.visibility = View.VISIBLE
        } else {
            binding.editDeleteDivider.visibility = View.GONE
            binding.buttonsContainer.visibility = View.GONE
        }

        // TODO: Implement recipe editing
        binding.recipeDeleteBtn.setOnClickListener {
            RecipesRepository.shared.deleteRecipe(recipe) {}
        }
    }

}