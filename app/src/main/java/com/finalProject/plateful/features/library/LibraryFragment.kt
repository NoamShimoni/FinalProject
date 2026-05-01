package com.finalProject.plateful.features.library

import androidx.fragment.app.viewModels
import com.finalProject.plateful.base.recipe_list.BaseRecipeListFragment

class LibraryFragment : BaseRecipeListFragment() {
    override val viewModel: LibraryViewModel by viewModels()
}