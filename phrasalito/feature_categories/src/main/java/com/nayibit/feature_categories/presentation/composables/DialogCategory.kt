package com.nayibit.feature_categories.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryStateUi
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent
import com.nayibit.utils.ui.composables.BaseDialog
import com.nayibit.utils.ui.composables.ButtonBase
import com.nayibit.utils.ui.composables.TextFieldBase
import com.nayibit.utils.ui.theme.primaryGradientEnd


@Composable
fun DialogCategory(
    showDialog: Boolean,
    colorButtons : Color = primaryGradientEnd,
    state: CategoryStateUi,
    onEvent: (CategoryUiEvent) -> Unit
) {
    BaseDialog(showDialog = showDialog) {
        TextFieldBase(value = state.title, onValueChange = { onEvent(CategoryUiEvent.OnTextChangeTitle(it)) }, label = "Titulo categoria")
        TextFieldBase(value = state.subtitle, onValueChange = { onEvent(CategoryUiEvent.OnTextChangeSubtitle(it)) }, label = "Subtitulo (opcional)")
        ButtonBase(text = "Aceptar", onClick = {  }, backgroundColor = colorButtons)
        ButtonBase(text = "Cancelar", onClick ={onEvent(CategoryUiEvent.ShowDialog(false))} , backgroundColor = colorButtons)
    }
}