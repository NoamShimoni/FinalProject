package com.finalProject.plateful.features.recipe_list

import androidx.fragment.app.viewModels
import com.finalProject.plateful.base.recipe_list.BaseRecipeListFragment

class RecipeListFragment : BaseRecipeListFragment() {
    override val viewModel: RecipesListViewModel by viewModels()
}