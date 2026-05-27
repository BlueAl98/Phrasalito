package com.nayibit.feature_categories.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_categories.R
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryStateUi
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent
import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.TypeModal
import com.nayibit.utils.ui.composables.BaseDialog
import com.nayibit.utils.ui.composables.ButtonBase
import com.nayibit.utils.ui.composables.TextFieldBase

private val categoryEmojis = listOf(
    "📚", "✏️", "🎓", "🔬", "🧪", "🔭",
    "💼", "💻", "📱", "📊", "📧", "🖥️",
    "✈️", "🗺️", "🌍", "🏖️", "🏔️", "🗼",
    "🍕", "🍔", "🥗", "🍜", "☕", "🎂",
    "⚽", "🏀", "🎾", "🏋️", "🚴", "🏊",
    "🌿", "🌺", "🌊", "☀️", "🌙", "⛄",
    "❤️", "⭐", "🎉", "🎁", "🏆", "🎨",
    "🎵", "🎭", "🎬", "🧘", "🐶", "🏠"
)

@Composable
fun DialogCategory(
    showDialog: Boolean,
    colorButtons: Color = MaterialTheme.colorScheme.secondary,
    state: CategoryStateUi,
    onEvent: (CategoryUiEvent) -> Unit,
    typeModal: TypeModal = TypeModal.CREATE,
    currentCategory: CategoryUi? = null
) {
    BaseDialog(showDialog = showDialog) {
        when (typeModal) {
            TypeModal.DELETE -> {
                Text(stringResource(R.string.label_delete_category_confirm, currentCategory?.title.orEmpty()))
                ButtonBase(text = stringResource(R.string.btn_accept), onClick = { onEvent(CategoryUiEvent.DeleteCategory(currentCategory!!)) }, backgroundColor = colorButtons)
                ButtonBase(text = stringResource(R.string.btn_cancel), onClick = { onEvent(CategoryUiEvent.DissmissDialog) }, backgroundColor = colorButtons)
            }
            else -> {
                IconPreview(selectedIcon = state.selectedIcon)

                Text(
                    text = stringResource(R.string.label_category_icon),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                EmojiPickerGrid(
                    selectedIcon = state.selectedIcon,
                    onIconSelected = { onEvent(CategoryUiEvent.OnIconChange(it)) }
                )

                TextFieldBase(
                    value = state.title,
                    onValueChange = { onEvent(CategoryUiEvent.OnTextChangeTitle(it)) },
                    label = stringResource(R.string.label_category_title),
                    maxChar = 20,
                    showCharCounter = true
                )
                TextFieldBase(
                    value = state.subtitle,
                    onValueChange = { onEvent(CategoryUiEvent.OnTextChangeSubtitle(it)) },
                    label = stringResource(R.string.label_category_subtitle),
                    maxChar = 20,
                    showCharCounter = true
                )
                ButtonBase(
                    text = stringResource(R.string.btn_accept),
                    onClick = {
                        if (typeModal == TypeModal.CREATE)
                            onEvent(CategoryUiEvent.InsertCategory(state.title, state.subtitle))
                        else
                            onEvent(CategoryUiEvent.UpdateCategory(currentCategory!!))
                    },
                    backgroundColor = colorButtons
                )
                ButtonBase(text = stringResource(R.string.btn_cancel), onClick = { onEvent(CategoryUiEvent.DissmissDialog) }, backgroundColor = colorButtons)
            }
        }
    }
}

@Composable
private fun IconPreview(selectedIcon: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
        ) {
            Text(
                text = selectedIcon.ifEmpty { "?" },
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmojiPickerGrid(
    selectedIcon: String,
    onIconSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier
            .fillMaxWidth()
            .height(168.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(categoryEmojis) { emoji ->
            val isSelected = selectedIcon == emoji
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent
                    )
                    .clickable { onIconSelected(emoji) }
                    .padding(6.dp)
            ) {
                Text(text = emoji, fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }
}
