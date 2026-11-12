package com.bjoey.app.archive.auth;

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
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/bjoey/app/archive/auth/AuthRoutes;", "", "()V", "GROUP", "", "LOGIN", "app_debug"})
public final class AuthRoutes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GROUP = "auth_group";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String LOGIN = "auth_login";
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.archive.auth.AuthRoutes INSTANCE = null;
    
    private AuthRoutes() {
        super();
    }
}