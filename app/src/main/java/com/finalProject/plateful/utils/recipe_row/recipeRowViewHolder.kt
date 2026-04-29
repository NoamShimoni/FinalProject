package com.finalProject.plateful.utils.recipe_row

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalProject.plateful.NavGraphDirections
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.databinding.RecipeRowLayoutBinding
import com.finalProject.plateful.models.Recipe
import com.squareup.picasso.Picasso

class RecipeRowViewHolder(
    private val binding: RecipeRowLayoutBinding,
    private val listener: OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    private var recipe: Recipe? = null

    init {
        itemView.setOnClickListener {
            recipe?.let { recipe ->
                listener?.onRecipeItemClick(recipe)
            }
        }

        binding.recipeEditBtn.setOnClickListener {
            recipe?.let { recipe ->
            val action = NavGraphDirections.actionGlobalEditRecipeFragment(
                recipe.id,
                recipe.title,
                recipe.ingredients,
                recipe.instructions,
                recipe.imageUrl,
                recipe.creatingUserId,
                recipe.creatingUserName,
            )
                it.findNavController().navigate(action)
            }
        }

        binding.recipeDeleteBtn.setOnClickListener {
            recipe?.let { recipe ->
                RecipesRepository.shared.deleteRecipe(recipe) {
                    RecipesRepository.shared.refreshRecipes()
                }
            }
        }
    }

    fun bind(recipe: Recipe, position: Int) {
        this.recipe = recipe

        binding.recipeTitleTextView.text = recipe.title

        Picasso.get().load(recipe.imageUrl).into(binding.recipeImageView)

        val isOwner = true //TODO: implement this check when Recipe model has a creatingUserId

        if (isOwner) {
            binding.editDeleteDivider.visibility = View.VISIBLE
            binding.buttonsContainer.visibility = View.VISIBLE
        } else {
            binding.editDeleteDivider.visibility = View.GONE
            binding.buttonsContainer.visibility = View.GONE
        }
    }
}