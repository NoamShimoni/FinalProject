package com.finalProject.plateful.dao

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.finalProject.plateful.base.MyApplication

object AppLocalDB {

    private val MIGRATION_ADD_CREATING_USER = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE Recipe ADD COLUMN creatingUserId TEXT NOT NULL DEFAULT ''")
        }
    }
    val db: AppLocalDbRepository by lazy {
        val context =
            MyApplication.appContext ?: throw IllegalStateException("Context is not initialized")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "recipes.db"
        ).addMigrations(MIGRATION_ADD_CREATING_USER).build()
    }
}
