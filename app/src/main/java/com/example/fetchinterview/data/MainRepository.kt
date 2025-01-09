package com.example.fetchinterview.data

import com.google.common.flogger.FluentLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject

enum class ConnectionState {
    SUCCESS,
    ERROR,
    INIT,
}
class MainRepository @Inject constructor(val nameApiService: NameApiService) {
    private var _connectionStateFlow = MutableSharedFlow<ConnectionState>()
    val connectionStateFlow = _connectionStateFlow.asSharedFlow()

    suspend fun getNames(): List<ViewItemBase> {
        try {
            val names = nameApiService.getNames()
            logger.atFine().log(names.toString())
            _connectionStateFlow.emit(ConnectionState.SUCCESS)
            return nameItemToViewItems(names)
        } catch(e: IOException) {
            logger.atWarning().log("Error retrieving names from internet %s", e.message)
            _connectionStateFlow.emit(ConnectionState.ERROR)
        }

        return listOf()// database get data
    }

    private fun nameItemToViewItems(names : List<NameItem>): List<ViewItemBase> {
        return names.filter { !it.name.isNullOrEmpty() }
            .sortedWith(
             compareBy ({ it.listId}, {it.name})
            ).map {
                ViewItemName(it)
            }
    }

    companion object {
        private val logger: FluentLogger = FluentLogger.forEnclosingClass()
    }
}