package com.example.fetchinterview.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Data Class for storing information from the Fetch Hiring Site.
 *
 * Used to parse incoming data.
 */
@Serializable
@Entity
data class NameItem(@PrimaryKey val id: Int, val name: String?, val listId: Int)