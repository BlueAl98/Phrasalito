package com.nayibit.feature_categories.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nayibit.utils.ui.composables.BaseDialog
import com.nayibit.utils.ui.composables.ButtonBase
import com.nayibit.utils.ui.composables.TextFieldBase
import com.nayibit.utils.ui.theme.primaryGradientEnd


@Composable
fun DialogCategory(
    showDialog: Boolean,
    colorButtons : Color = primaryGradientEnd,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    BaseDialog(showDialog = showDialog) {
        TextFieldBase(value = "", onValueChange = {}, label = "titulo categoria")
        ButtonBase(text = "Aceptar", onClick = onAccept, backgroundColor = colorButtons)
        ButtonBase(text = "Cancelar", onClick = onDismiss, backgroundColor = colorButtons)
    }
}