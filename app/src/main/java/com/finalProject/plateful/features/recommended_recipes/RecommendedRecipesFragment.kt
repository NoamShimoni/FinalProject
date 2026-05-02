package com.finalProject.plateful.features.recommended_recipes

import androidx.fragment.app.viewModels
import com.finalProject.plateful.base.recipe_list.BaseRecipeListFragment

class RecommendedRecipesFragment : BaseRecipeListFragment() {
    override val viewModel: RecommendedRecipesViewModel by viewModels()
}
