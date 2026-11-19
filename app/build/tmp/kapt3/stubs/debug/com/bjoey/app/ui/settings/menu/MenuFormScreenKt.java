package com.bjoey.app.ui.settings.menu;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u00c2\u0001\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052$\u0010\u0007\u001a \u0012\u0004\u0012\u00020\t\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\n\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f2b\u0010\r\u001a^\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0014\u0012\u0015\u0012\u0013\u0018\u00010\t\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a.\u0010\u0018\u001a\u00020\u00012\b\u0010\u0015\u001a\u0004\u0018\u00010\t2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\b\u0010\u001b\u001a\u00020\u001cH\u0003\u00a8\u0006\u001d"}, d2 = {"MenuFormScreen", "", "existingItem", "Lcom/bjoey/app/ui/settings/menu/MenuItem;", "categories", "", "Lcom/bjoey/app/ui/settings/menu/MenuCategory;", "onAddCategory", "Lkotlin/Function2;", "", "Lkotlin/Function1;", "onBackClick", "Lkotlin/Function0;", "onSaveClick", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "title", "", "price", "categoryId", "imageUri", "modifier", "Landroidx/compose/ui/Modifier;", "MenuPhotoPickerField", "onPickImageClick", "onRemoveImageClick", "menuInputColors", "Landroidx/compose/material3/TextFieldColors;", "app_debug"})
public final class MenuFormScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MenuFormScreen(@org.jetbrains.annotations.Nullable()
    com.bjoey.app.ui.settings.menu.MenuItem existingItem, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bjoey.app.ui.settings.menu.MenuCategory> categories, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super kotlin.jvm.functions.Function1<? super com.bjoey.app.ui.settings.menu.MenuCategory, kotlin.Unit>, kotlin.Unit> onAddCategory, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function4<? super java.lang.String, ? super java.lang.Long, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSaveClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MenuPhotoPickerField(java.lang.String imageUri, kotlin.jvm.functions.Function0<kotlin.Unit> onPickImageClick, kotlin.jvm.functions.Function0<kotlin.Unit> onRemoveImageClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final androidx.compose.material3.TextFieldColors menuInputColors() {
        return null;
    }
}