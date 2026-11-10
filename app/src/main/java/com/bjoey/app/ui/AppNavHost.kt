package com.bjoey.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bjoey.app.ui.auth.AuthRoutes
import com.bjoey.app.ui.auth.authGraph
import com.bjoey.app.ui.cashier.CashierRoutes
import com.bjoey.app.ui.cashier.cashierGraph

/**
 * ============================================================================
 * NEXT.JS ANALOGY:
 * File ini setara dengan: Root Router / Root Layout `app/layout.tsx`
 *
 * Di sini kita menggabungkan semua "Folder Group Routes":
 * - Grup (auth)    -> via authGraph()
 * - Grup (cashier) -> via cashierGraph()
 *
 * Di Next.js:
 * router.push('/dashboard') atau router.replace('/login')
 *
 * Di Compose Navigation:
 * navController.navigate(...) dengan opsi popUpTo untuk membersihkan history stack
 * ============================================================================
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        // Halaman awal aplikasi saat pertama dibuka: grup (auth)
        startDestination = AuthRoutes.GROUP
    ) {
        // Group Route 1: (auth)
        authGraph(
            onLoginSuccess = { cashierName ->
                // Navigasi ke (cashier) dan hapus history auth (setara router.replace)
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
                // Logout dan kembali ke (auth), hapus session cashier dari backstack
                navController.navigate(AuthRoutes.GROUP) {
                    popUpTo(CashierRoutes.GROUP) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

