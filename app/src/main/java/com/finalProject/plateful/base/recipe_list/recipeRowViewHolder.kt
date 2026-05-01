package com.finalProject.plateful.base.recipe_list

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.finalProject.plateful.NavGraphDirections
import com.finalProject.plateful.databinding.RecipeRowLayoutBinding
import com.finalProject.plateful.models.Recipe
import com.squareup.picasso.Picasso

class RecipeRowViewHolder(
    private val binding: RecipeRowLayoutBinding,
    private val listener: OnItemClickListener?,
    private val viewModel: IRecipesViewModel
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
                viewModel.deleteRecipe(recipe)
            }
        }
    }

    fun bind(recipe: Recipe, position: Int) {
        this.recipe = recipe

        binding.recipeTitleTextView.text = recipe.title
        binding.recipeCreatorTextView.text = recipe.creatingUserName

        Picasso.get().load(recipe.imageUrl).into(binding.recipeImageView)

        val isOwner = recipe.creatingUserId == viewModel.getCurrentUser()?.uid

        if (isOwner) {
            binding.buttonsContainer.visibility = View.VISIBLE
        } else {
            binding.buttonsContainer.visibility = View.GONE
        }
    }
}