package com.finalProject.plateful.base

import com.finalProject.plateful.models.Recipe

typealias RecipesCompletion = (List<Recipe>) -> Unit
typealias Completion = () -> Unit
typealias StringCompletion = (String?) -> Unit
typealias BooleanCompletion = (Boolean) -> Unit