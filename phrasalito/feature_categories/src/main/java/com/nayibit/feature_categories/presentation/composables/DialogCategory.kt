package com.nayibit.feature_categories.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nayibit.feature_categories.R
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
    colorButtons : Color = MaterialTheme.colorScheme.secondary,
    state: CategoryStateUi,
    onEvent: (CategoryUiEvent) -> Unit,
    typeModal: TypeModal = TypeModal.CREATE,
    currentCategory: CategoryUi? = null
) {
    BaseDialog(showDialog = showDialog) {


        when(typeModal){
         TypeModal.DELETE -> {
             Text(stringResource(R.string.label_delete_category_confirm, currentCategory?.title.orEmpty()))
             ButtonBase(text = stringResource(R.string.btn_accept), onClick = { onEvent(CategoryUiEvent.DeleteCategory(currentCategory!!))}, backgroundColor = colorButtons)
             ButtonBase(text = stringResource(R.string.btn_cancel), onClick = { onEvent(CategoryUiEvent.DissmissDialog)}, backgroundColor = colorButtons)
         }
        else ->  {
             TextFieldBase(value = state.title,
                 onValueChange = { onEvent(CategoryUiEvent.OnTextChangeTitle(it)) },
                 label = stringResource(R.string.label_category_title),
                 maxChar = 20,
                 showCharCounter = true
                 )
             TextFieldBase(value = state.subtitle,
                 onValueChange = { onEvent(CategoryUiEvent.OnTextChangeSubtitle(it)) },
                 label = stringResource(R.string.label_category_subtitle),
                 maxChar = 20,
                 showCharCounter = true
                 )
             ButtonBase(text = stringResource(R.string.btn_accept),
                 onClick = {
                     if (typeModal == TypeModal.CREATE)
                        onEvent(CategoryUiEvent.InsertCategory(state.title, state.subtitle))
                     else
                         onEvent(CategoryUiEvent.UpdateCategory(currentCategory!!))
                    },
                 backgroundColor = colorButtons)
             ButtonBase(text = stringResource(R.string.btn_cancel), onClick ={onEvent(CategoryUiEvent.DissmissDialog)} , backgroundColor = colorButtons)}
        }

    }
}