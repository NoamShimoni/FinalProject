package com.finalProject.plateful.features.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalProject.plateful.databinding.RecipeRowLayoutBinding
import com.finalProject.plateful.models.Recipe

interface OnItemClickListener {
    fun onRecipeItemClick(recipe: Recipe)
}

class RecipesAdapter (
     var recipes: MutableList<Recipe>?
): RecyclerView.Adapter<RecipeRowViewHolder>() {

    var listener: OnItemClickListener? = null
    override fun getItemCount(): Int = recipes?.size ?: 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeRowViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = RecipeRowLayoutBinding.inflate(inflator, parent, false)

        return RecipeRowViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        holder: RecipeRowViewHolder,
        position: Int
    ) {
        recipes?.let {
            holder.bind(it[position], position)
        }
    }
}