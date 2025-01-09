package com.example.fetchinterview.data

enum class ViewType {
    LIST,
    NAME,
}

/**
 * Base class for holding data for [RecyclerView]
 */
open class ViewItemBase(val type: ViewType)