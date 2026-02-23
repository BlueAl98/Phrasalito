package com.nayibit.feature_categories.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryStateUi
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent
import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.TypeModal
import com.nayibit.utils.ui.composables.BaseDialog
import com.nayibit.utils.ui.composables.ButtonBase
import com.nayibit.utils.ui.composables.TextFieldBase
import com.nayibit.utils.ui.theme.primaryGradientEnd


@Composable
fun DialogCategory(
    showDialog: Boolean,
    colorButtons : Color = primaryGradientEnd,
    state: CategoryStateUi,
    onEvent: (CategoryUiEvent) -> Unit,
    typeModal: TypeModal = TypeModal.CREATE,
    currentCategory: CategoryUi? = null
) {
    BaseDialog(showDialog = showDialog) {


        when(typeModal){
         TypeModal.DELETE -> {
             Text("Desea eliminar la categoria ${currentCategory?.title}?")
             ButtonBase(text = "Aceptar", onClick = { onEvent(CategoryUiEvent.DeleteCategory(currentCategory!!))}, backgroundColor = colorButtons)
             ButtonBase(text = "Cancelar", onClick = { onEvent(CategoryUiEvent.DissmissDialog)}, backgroundColor = colorButtons)
         }
        else ->  {
             TextFieldBase(value = state.title, onValueChange = { onEvent(CategoryUiEvent.OnTextChangeTitle(it)) }, label = "Titulo categoria")
             TextFieldBase(value = state.subtitle, onValueChange = { onEvent(CategoryUiEvent.OnTextChangeSubtitle(it)) }, label = "Subtitulo (opcional)")
             ButtonBase(text = "Aceptar",
                 onClick = {
                     if (typeModal == TypeModal.CREATE)
                        onEvent(CategoryUiEvent.InsertCategory(state.title, state.subtitle))
                     else
                         onEvent(CategoryUiEvent.UpdateCategory(currentCategory!!))
                    },
                 backgroundColor = colorButtons)
             ButtonBase(text = "Cancelar", onClick ={onEvent(CategoryUiEvent.DissmissDialog)} , backgroundColor = colorButtons)}
        }

    }
}