package com.finalProject.plateful.dao

import androidx.room.Room
import com.finalProject.plateful.base.MyApplication

object AppLocalDB {
    val db: AppLocalDbRepository by lazy {
        val context =
            MyApplication.appContext ?: throw IllegalStateException("Context is not initialized")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "recipes.db"
        )
        .fallbackToDestructiveMigration(true)
        .build()
    }
}
