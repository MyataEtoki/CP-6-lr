package com.example.a6lr

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Parrot::class], version = 1, exportSchema = false)
abstract class ParrotDatabase : RoomDatabase() {
    abstract fun parrotDao(): ParrotDao

    companion object {
        @Volatile
        private var INSTANCE: ParrotDatabase? = null

        fun getDatabase(context: Context): ParrotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParrotDatabase::class.java,
                    "parrot_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}