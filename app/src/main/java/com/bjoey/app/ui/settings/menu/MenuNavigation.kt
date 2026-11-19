package com.bjoey.app.ui.settings.menu

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
import com.bjoey.app.data.MenuDao

private const val MENU_LIST_ROUTE = "menu_list"
private const val MENU_FORM_BASE_ROUTE = "menu_form"
private const val ARG_ITEM_ID = "itemId"
private const val MENU_FORM_ROUTE = "$MENU_FORM_BASE_ROUTE?$ARG_ITEM_ID={$ARG_ITEM_ID}"

@Composable
fun MenuNavigation(
    menuDao: MenuDao,
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = viewModel(factory = MenuViewModel.Factory(menuDao)),
    onBackClick: (() -> Unit)? = null
) {
    val navController = rememberNavController()
    val allItems by viewModel.menuItems.collectAsStateWithLifecycle()
    val filteredItems by viewModel.filteredMenuItems.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategoryId by viewModel.selectedCategoryId.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = MENU_LIST_ROUTE,
        modifier = modifier
    ) {
        composable(route = MENU_LIST_ROUTE) {
            MenuListScreen(
                menuItems = filteredItems,
                categories = categories,
                selectedCategoryId = selectedCategoryId,
                totalCount = allItems.size,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onCategorySelected = viewModel::onCategorySelected,
                onAddCategory = { name -> viewModel.addCategory(name) },
                onAddClick = {
                    navController.navigate(MENU_FORM_BASE_ROUTE)
                },
                onEditClick = { itemId ->
                    navController.navigate("$MENU_FORM_BASE_ROUTE?$ARG_ITEM_ID=$itemId")
                },
                onDeleteConfirm = { item ->
                    viewModel.deleteMenuItem(item.id)
                },
                onBackClick = onBackClick
            )
        }

        composable(
            route = MENU_FORM_ROUTE,
            arguments = listOf(
                navArgument(ARG_ITEM_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(ARG_ITEM_ID)
            val existingItem = itemId?.let(viewModel::getMenuItemById)

            MenuFormScreen(
                existingItem = existingItem,
                categories = categories,
                onAddCategory = viewModel::addCategory,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { title, price, categoryId, imageUri ->
                    if (existingItem != null) {
                        viewModel.updateMenuItem(
                            id = existingItem.id,
                            title = title,
                            price = price,
                            categoryId = categoryId,
                            imageUri = imageUri
                        )
                    } else {
                        viewModel.addMenuItem(
                            title = title,
                            price = price,
                            categoryId = categoryId,
                            imageUri = imageUri
                        )
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}
