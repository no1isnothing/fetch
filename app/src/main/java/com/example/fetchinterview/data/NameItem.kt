package com.example.fetchinterview.data

import kotlinx.serialization.Serializable

/**
 * Data Class for storing information from the Fetch Hiring Site.
 *
 * Used to parse incoming data.
 */
@Serializable
data class NameItem(val id: Int, val name: String?, val listId: Int)