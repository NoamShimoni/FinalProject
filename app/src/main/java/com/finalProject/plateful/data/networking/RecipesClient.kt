package com.finalProject.plateful.data.networking

import com.finalProject.plateful.models.RemoteRecipes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesClient {
    @GET("search.php")
    fun getRecipesByName(
        @Query("s") recipeName: String,
    ): Call<RemoteRecipes>
}