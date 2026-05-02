package com.finalProject.plateful.data.repositories.remote_recipes

import com.finalProject.plateful.data.networking.NetworkClient
import com.finalProject.plateful.models.RemoteRecipe
import com.finalProject.plateful.models.RemoteRecipes

class RemoteRecipesRepository : RecipesRepository {
    companion object {
        val shared = RemoteRecipesRepository()
    }

    override fun getRecipes(): RemoteRecipes {
        val request = NetworkClient.recipesApiClient.getRecipesByName("")
        val response = request.execute()

        return when (response.isSuccessful) {
            true -> {
                response.body() ?: run {
                    RemoteRecipes(mutableListOf())
                }
            }

            false -> {
                RemoteRecipes(mutableListOf())
            }
        }
    }

}