package com.finalProject.plateful.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.finalProject.plateful.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class Recipe(
    @PrimaryKey
    val id: String,
    val title: String,
    var ingredients: String,
    val instructions: String,
    val imageUrl: String,
    val lastUpdated: Long?
    ) {
    companion object {
        var lastUpdated: Long
            get() {
                return MyApplication.Globals.appContext
                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.getLong(LAST_UPDATED_KEY, 0) ?: 0
            }
            set(value) {
                MyApplication.Globals.appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putLong(LAST_UPDATED_KEY, value)
                    ?.apply()
            }

        const val ID_KEY = "id"
        const val TITLE_KEY = "title"
        const val INGREDIENTS_KEY = "ingredients"

        const val INSTRUCTIONS_KEY = "instructions"
        const val IMAGE_URL_KEY = "imageUrl"
        const val LAST_UPDATED_KEY = "lastUpdated"



        fun fromJson(json: Map<String, Any>): Recipe {
            val id = json[ID_KEY] as? String ?: ""
            val title = json[TITLE_KEY] as? String ?: ""
            val ingredients = json[INGREDIENTS_KEY] as? String ?: ""
            val instructions = json[INSTRUCTIONS_KEY] as? String ?: ""
            val imageUrl = json[IMAGE_URL_KEY] as? String ?: ""
            val timestamp = json[LAST_UPDATED_KEY] as? Timestamp
            val lastUpdatedLong = timestamp?.toDate()?.time

            return Recipe(
                id = id,
                title = title,
                ingredients = ingredients,
                instructions = instructions,
                imageUrl = imageUrl,
                lastUpdated = lastUpdatedLong
            )
        }
    }

    val toJson: Map<String, Any?>
        get() = hashMapOf(
            ID_KEY to id,
            TITLE_KEY to title,
            INGREDIENTS_KEY to ingredients,
            INSTRUCTIONS_KEY to instructions,
            IMAGE_URL_KEY to imageUrl,
            LAST_UPDATED_KEY to FieldValue.serverTimestamp()
        )
}