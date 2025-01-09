package com.example.fetchinterview.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * A dao for storing [NameItem]s
 */
@Dao
interface NameDao {
    @Query("SELECT * FROM nameitem")
    suspend fun getAllNameItems(): List<NameItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(names: List<NameItem>)
}