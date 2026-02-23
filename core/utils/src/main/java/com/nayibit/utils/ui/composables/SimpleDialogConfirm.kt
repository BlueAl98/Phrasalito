package com.nayibit.utils.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SimpleConfirmDialog(
    modifier: Modifier = Modifier,
    title: String = "Title",
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(title, style = MaterialTheme.typography.titleMedium) },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(modifier= modifier.clickable{onCancel()}, text = "Cancelar", color = Color.Red)
                Text(modifier= modifier.clickable{onConfirm()}, text = "Confirmar")

            }
        }
    )
}