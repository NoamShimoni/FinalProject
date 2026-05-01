package com.finalProject.plateful.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalProject.plateful.data.repositories.recipes.RecipesRepository
import com.finalProject.plateful.models.Recipe
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class LibraryViewModel: ViewModel() {
    var data: LiveData<MutableList<Recipe>> = RecipesRepository.shared.getAllRecipes(Firebase.auth.currentUser?.uid)
    val isRefreshing = MutableLiveData<Boolean>()

    fun refreshRecipes() {
        isRefreshing.value = true
        RecipesRepository.shared.refreshRecipes {
            isRefreshing.postValue(false)
        }
    }
}