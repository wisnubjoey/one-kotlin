package com.bjoey.app.archive.cashier;

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
 *  (setara `useParams()` di Next.js)
 * ============================================================================
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/bjoey/app/archive/cashier/CashierRoutes;", "", "()V", "DASHBOARD", "", "GROUP", "createDashboardRoute", "cashierName", "app_debug"})
public final class CashierRoutes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String GROUP = "cashier_group";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DASHBOARD = "cashier_dashboard/{cashierName}";
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.archive.cashier.CashierRoutes INSTANCE = null;
    
    private CashierRoutes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String createDashboardRoute(@org.jetbrains.annotations.NotNull()
    java.lang.String cashierName) {
        return null;
    }
}