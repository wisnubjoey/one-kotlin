package com.bjoey.app.archive.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/**
 * ============================================================================
 * [ARCHIVE]
 * NEXT.JS ANALOGY:
 * File ini setara dengan: `app/(auth)/_layout.tsx`
 *
 * Di Next.js:
 * Folder `(auth)` membungkus route tanpa mengubah URL, dan menyatukan layout auth.
 *
 * Di Jetpack Compose:
 * Kita menggunakan `navigation(startDestination = ..., route = "auth_group") { ... }`
 * Ini dinamakan "Nested NavGraph". Semua layar di bawah grup ini terisolasi rapi.
 * ============================================================================
 */
object AuthRoutes {
    const val GROUP = "auth_group"
    const val LOGIN = "auth_login"
}

fun NavGraphBuilder.authGraph(
    onLoginSuccess: (cashierName: String) -> Unit
) {
    // 'navigation()' di sini membuat Group Route 'auth_group'
    navigation(
        startDestination = AuthRoutes.LOGIN,
        route = AuthRoutes.GROUP
    ) {
        composable(AuthRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = onLoginSuccess
            )
        }
    }
}

