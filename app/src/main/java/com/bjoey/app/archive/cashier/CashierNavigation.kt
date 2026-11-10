package com.bjoey.app.archive.cashier

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

/**
 * ============================================================================
 * [ARCHIVE]
 * NEXT.JS ANALOGY:
 * File ini setara dengan: `app/(cashier)/_layout.tsx`
 *
 * Di Next.js:
 * Mengatur dynamic route seperti `app/(cashier)/dashboard/[cashierName]/page.tsx`
 *
 * Di Jetpack Compose:
 * - Kita mendefinisikan rute dengan placeholder parameter: "cashier_dashboard/{cashierName}"
 * - Mengambil parameter via `backStackEntry.arguments?.getString("cashierName")`
 *   (setara `useParams()` di Next.js)
 * ============================================================================
 */
object CashierRoutes {
    const val GROUP = "cashier_group"
    const val DASHBOARD = "cashier_dashboard/{cashierName}"

    fun createDashboardRoute(cashierName: String): String {
        return "cashier_dashboard/$cashierName"
    }
}

fun NavGraphBuilder.cashierGraph(
    onLogout: () -> Unit
) {
    // Nested NavGraph untuk grup (cashier)
    navigation(
        startDestination = CashierRoutes.DASHBOARD,
        route = CashierRoutes.GROUP
    ) {
        composable(
            route = CashierRoutes.DASHBOARD,
            arguments = listOf(
                navArgument("cashierName") {
                    type = NavType.StringType
                    defaultValue = "Kasir"
                }
            )
        ) { backStackEntry ->
            val cashierName = backStackEntry.arguments?.getString("cashierName") ?: "Kasir"
            CashierDashboardScreen(
                cashierName = cashierName,
                onLogout = onLogout
            )
        }
    }
}

