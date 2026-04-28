package com.finalProject.plateful.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.finalProject.plateful.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
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
        const val USERNAME_KEY = "username"
        const val EMAIL_KEY = "email"

        const val PASSWORD_KEY = "password"
        const val IMAGE_URL_KEY = "imageUrl"
        const val LAST_UPDATED_KEY = "lastUpdated"



        fun fromJson(json: Map<String, Any>): User {
            val id = json[ID_KEY] as? String ?: ""
            val username = json[USERNAME_KEY] as? String ?: ""
            val timestamp = json[LAST_UPDATED_KEY] as? Timestamp
            val lastUpdatedLong = timestamp?.toDate()?.time

            return User(
                id = id,
                username = username,
                lastUpdated = lastUpdatedLong
            )
        }
    }

    val toJson: Map<String, Any?>
        get() = hashMapOf(
            ID_KEY to id,
            USERNAME_KEY to username,
            LAST_UPDATED_KEY to FieldValue.serverTimestamp()
        )
}