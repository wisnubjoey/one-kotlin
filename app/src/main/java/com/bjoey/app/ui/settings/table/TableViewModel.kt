package com.bjoey.app.ui.settings.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bjoey.app.data.local.dao.TableDao
import com.bjoey.app.data.local.entity.DiningTableEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Room-backed ViewModel managing POS tables and live search filtering.
 */
class TableViewModel(
    private val tableDao: TableDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    val tables: StateFlow<List<TableItem>> = tableDao.observeTables()
        .map { entities ->
            entities.map { entity ->
                TableItem(
                    id = entity.id,
                    name = entity.name
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredTables: StateFlow<List<TableItem>> = combine(
        tables,
        _searchQuery
    ) { items, query ->
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            items
        } else {
            items.filter { it.name.contains(trimmed, ignoreCase = true) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun getTableById(id: String): TableItem? {
        return tables.value.firstOrNull { it.id == id }
    }

    fun addTable(name: String) {
        val cleanName = normalizeTableName(name)
        if (cleanName.isEmpty()) return

        val entity = DiningTableEntity(
            id = UUID.randomUUID().toString(),
            name = cleanName,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch(ioDispatcher) {
            tableDao.insertTable(entity)
        }
    }

    fun updateTable(id: String, name: String) {
        val cleanName = normalizeTableName(name)
        if (cleanName.isEmpty()) return

        val entity = DiningTableEntity(
            id = id,
            name = cleanName,
            updatedAt = System.currentTimeMillis()
        )
        viewModelScope.launch(ioDispatcher) {
            tableDao.updateTable(entity)
        }
    }

    fun deleteTable(id: String) {
        viewModelScope.launch(ioDispatcher) {
            tableDao.deleteTableById(id)
        }
    }

    class Factory(private val tableDao: TableDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TableViewModel::class.java)) {
                return TableViewModel(tableDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
