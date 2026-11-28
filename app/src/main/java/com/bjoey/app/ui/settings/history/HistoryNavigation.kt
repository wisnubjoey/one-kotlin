package com.bjoey.app.ui.settings.history

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
import com.bjoey.app.data.local.dao.OrderDao

private const val HISTORY_LIST_ROUTE = "history_list"
private const val HISTORY_DETAIL_BASE_ROUTE = "history_detail"
private const val ARG_ORDER_ID = "orderId"
private const val HISTORY_DETAIL_ROUTE = "$HISTORY_DETAIL_BASE_ROUTE/{$ARG_ORDER_ID}"

@Composable
fun HistoryNavigation(
    orderDao: OrderDao,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(factory = HistoryViewModel.Factory(orderDao)),
    onBackClick: (() -> Unit)? = null
) {
    val navController = rememberNavController()
    val filteredOrders by viewModel.filteredOrders.collectAsStateWithLifecycle()
    val summary by viewModel.summary.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedStatusFilter by viewModel.selectedStatusFilter.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = HISTORY_LIST_ROUTE,
        modifier = modifier
    ) {
        composable(route = HISTORY_LIST_ROUTE) {
            HistoryListScreen(
                orders = filteredOrders,
                summary = summary,
                selectedStatusFilter = selectedStatusFilter,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onStatusFilterSelected = viewModel::onStatusFilterSelected,
                onOrderClick = { orderId ->
                    navController.navigate("$HISTORY_DETAIL_BASE_ROUTE/$orderId")
                },
                onBackClick = onBackClick
            )
        }

        composable(
            route = HISTORY_DETAIL_ROUTE,
            arguments = listOf(
                navArgument(ARG_ORDER_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString(ARG_ORDER_ID)
            val entry = orderId?.let(viewModel::getOrderById)

            HistoryDetailScreen(
                entry = entry,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
