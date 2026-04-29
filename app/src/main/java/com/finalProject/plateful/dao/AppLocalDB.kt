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
    private val MIGRATION_ADD_CREATING_USERNAME = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE Recipe ADD COLUMN creatingUserName TEXT NOT NULL DEFAULT ''")
        }
    }

    private val MIGRATION_ADD_IS_DELETED = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE Recipe ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
        }
    }
    val db: AppLocalDbRepository by lazy {
        val context =
            MyApplication.appContext ?: throw IllegalStateException("Context is not initialized")

        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "recipes.db"
        ).addMigrations(MIGRATION_ADD_CREATING_USER).addMigrations(MIGRATION_ADD_CREATING_USERNAME).addMigrations(MIGRATION_ADD_IS_DELETED)
            .build()
    }
}
