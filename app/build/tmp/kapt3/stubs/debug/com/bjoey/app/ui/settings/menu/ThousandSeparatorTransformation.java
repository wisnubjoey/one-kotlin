package com.bjoey.app.ui.settings.menu;

/**
 * Formats raw digit strings (e.g. "28000") into dot-grouped numbers ("28.000")
 * while maintaining accurate cursor mapping.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/bjoey/app/ui/settings/menu/ThousandSeparatorTransformation;", "Landroidx/compose/ui/text/input/VisualTransformation;", "()V", "filter", "Landroidx/compose/ui/text/input/TransformedText;", "text", "Landroidx/compose/ui/text/AnnotatedString;", "Companion", "app_debug"})
public final class ThousandSeparatorTransformation implements androidx.compose.ui.text.input.VisualTransformation {
    @org.jetbrains.annotations.NotNull()
    public static final com.bjoey.app.ui.settings.menu.ThousandSeparatorTransformation.Companion Companion = null;
    
    public ThousandSeparatorTransformation() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public androidx.compose.ui.text.input.TransformedText filter(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.text.AnnotatedString text) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/bjoey/app/ui/settings/menu/ThousandSeparatorTransformation$Companion;", "", "()V", "formatDigitsWithDots", "", "digits", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String formatDigitsWithDots(@org.jetbrains.annotations.NotNull()
        java.lang.String digits) {
            return null;
        }
    }
}