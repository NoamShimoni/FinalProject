package com.finalProject.plateful.data.repositories.remote_recipes

import com.finalProject.plateful.models.RemoteRecipes

interface RecipesRepository {
    fun getRecipes(): RemoteRecipes
}