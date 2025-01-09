package com.example.fetchinterview.data

import com.google.common.flogger.FluentLogger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.IOException
import javax.inject.Inject

enum class ConnectionState {
    SUCCESS,
    ERROR,
}

/**
 * MainRepository for retrieving [NameItem]s from various sources.
 *
 * The preferred source is the [NameApiService], but it falls back to the [NameDao].
 */
class MainRepository @Inject constructor(val nameApiService: NameApiService, val nameDao: NameDao) {
    private var _connectionStateFlow = MutableSharedFlow<ConnectionState>()
    val connectionStateFlow = _connectionStateFlow.asSharedFlow()

    suspend fun getNames(): List<ViewItemBase> {
        try {
            val names = nameApiService.getNames()
            logger.atInfo().log(names.toString())
            _connectionStateFlow.emit(ConnectionState.SUCCESS)
            nameDao.insertOrUpdate(names)
            return nameItemsToSortedViewItems(names)
        } catch(e: IOException) {
            logger.atWarning().log("Error retrieving names from internet %s", e.message)
            _connectionStateFlow.emit(ConnectionState.ERROR)
        }

        return nameItemsToSortedViewItems(nameDao.getAllNameItems())
    }

    private fun nameItemsToSortedViewItems(names : List<NameItem>): List<ViewItemBase> {
        val sortedNames = names.filter { !it.name.isNullOrEmpty() }
            .sortedWith(
             compareBy ({ it.listId}, {it.name})
            ).map {
                ViewItemName(it)
            }.toMutableList<ViewItemBase>()
        logger.atWarning().log(sortedNames.size.toString())
        var currentListId = (sortedNames[0] as ViewItemName).item.listId
        sortedNames.add(0, ViewItemList(currentListId))
        val iterator = sortedNames.listIterator()
        iterator.next()
        while(iterator.hasNext()) {
            val nameItem = iterator.next() as ViewItemName
            if (nameItem.item.listId != currentListId){
                logger.atFine().log("New list id %d", nameItem.item.listId)
                iterator.previous()
                currentListId = nameItem.item.listId
                iterator.add(ViewItemList(currentListId))
                iterator.next()
            }
        }
        return sortedNames
    }

    companion object {
        private val logger: FluentLogger = FluentLogger.forEnclosingClass()
    }
}