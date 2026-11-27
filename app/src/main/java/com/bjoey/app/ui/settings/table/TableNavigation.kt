package com.bjoey.app.ui.settings.table

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bjoey.app.data.local.dao.TableDao

private const val TABLE_LIST_ROUTE = "table_list"
private const val TABLE_FORM_BASE_ROUTE = "table_form"
private const val ARG_TABLE_ID = "tableId"
private const val TABLE_FORM_ROUTE = "$TABLE_FORM_BASE_ROUTE?$ARG_TABLE_ID={$ARG_TABLE_ID}"

@Composable
fun TableNavigation(
    tableDao: TableDao,
    modifier: Modifier = Modifier,
    viewModel: TableViewModel = viewModel(factory = TableViewModel.Factory(tableDao)),
    onBackClick: (() -> Unit)? = null
) {
    val navController = rememberNavController()
    val allTables by viewModel.tables.collectAsStateWithLifecycle()
    val filteredTables by viewModel.filteredTables.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = TABLE_LIST_ROUTE,
        modifier = modifier
    ) {
        composable(route = TABLE_LIST_ROUTE) {
            TableListScreen(
                tables = filteredTables,
                totalCount = allTables.size,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onAddClick = {
                    navController.navigate(TABLE_FORM_BASE_ROUTE)
                },
                onEditClick = { tableId ->
                    navController.navigate("$TABLE_FORM_BASE_ROUTE?$ARG_TABLE_ID=$tableId")
                },
                onDeleteConfirm = { table ->
                    viewModel.deleteTable(table.id)
                },
                onBackClick = onBackClick
            )
        }

        composable(
            route = TABLE_FORM_ROUTE,
            arguments = listOf(
                navArgument(ARG_TABLE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val tableId = backStackEntry.arguments?.getString(ARG_TABLE_ID)
            val existingTable = tableId?.let(viewModel::getTableById)

            TableFormScreen(
                existingTable = existingTable,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { name ->
                    if (existingTable != null) {
                        viewModel.updateTable(id = existingTable.id, name = name)
                    } else {
                        viewModel.addTable(name = name)
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}
