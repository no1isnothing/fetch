package com.example.fetchinterview.data

enum class ViewType {
    LIST,
    NAME,
}
open class ViewItemBase(val type: ViewType)