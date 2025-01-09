package com.example.fetchinterview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * A Room database for storing [NameItem]s.
 */
@Database(entities = [NameItem::class], version = 1)
abstract class MainDatabase : RoomDatabase()  {
    abstract fun nameDao(): NameDao
}