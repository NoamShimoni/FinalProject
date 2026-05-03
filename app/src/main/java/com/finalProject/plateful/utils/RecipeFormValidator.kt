package com.finalProject.plateful.utils

import android.content.Context
import com.finalProject.plateful.databinding.FragmentAddRecipeBinding

object RecipeFormValidator {
    fun validateForm(binding: FragmentAddRecipeBinding, context: Context?): Boolean {
        var isValid = true
        val invalidFields = mutableListOf<String>()

        val title = binding.recipeTitleTextInput.text.toString().trim()
        val ingredients = binding.recipeIngredientsTextInput.text.toString().trim()
        val instructions = binding.recipeInstructionsTextInput.text.toString().trim()

        if (title.isEmpty()) {
            binding.recipeTitleTextInputLayout.error = "Title is required"
            invalidFields.add("title")
            isValid = false
        } else {
            binding.recipeTitleTextInputLayout.error = null
        }

        if (ingredients.isEmpty()) {
            binding.recipeIngredientsTextInputLayout.error = "Ingredients are required"
            invalidFields.add("ingredients")
            isValid = false
        } else {
            binding.recipeIngredientsTextInputLayout.error = null
        }

        if (instructions.isEmpty()) {
            binding.recipeInstructionsTextInputLayout.error = "Instructions are required"
            invalidFields.add("instructions")
            isValid = false
        } else {
            binding.recipeInstructionsTextInputLayout.error = null
        }

        return isValid
    }
}
