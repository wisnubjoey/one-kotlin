package com.bjoey.app.archive

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bjoey.app.archive.auth.AuthRoutes
import com.bjoey.app.archive.auth.authGraph
import com.bjoey.app.archive.cashier.CashierRoutes
import com.bjoey.app.archive.cashier.cashierGraph

/**
 * ============================================================================
 * [ARCHIVE]
 * NEXT.JS ANALOGY:
 * File ini setara dengan: Root Router / Root Layout `app/layout.tsx`
 *
 * Menggabungkan folder group:
 * - (auth)    -> via authGraph()
 * - (cashier) -> via cashierGraph()
 * ============================================================================
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoutes.GROUP
    ) {
        // Group Route 1: (auth)
        authGraph(
            onLoginSuccess = { cashierName ->
                navController.navigate(CashierRoutes.createDashboardRoute(cashierName)) {
                    popUpTo(AuthRoutes.GROUP) {
                        inclusive = true
                    }
                }
            }
        )

        // Group Route 2: (cashier)
        cashierGraph(
            onLogout = {
                navController.navigate(AuthRoutes.GROUP) {
                    popUpTo(CashierRoutes.GROUP) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

