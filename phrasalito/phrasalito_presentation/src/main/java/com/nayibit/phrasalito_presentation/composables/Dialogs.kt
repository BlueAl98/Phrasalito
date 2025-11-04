package com.nayibit.phrasalito_presentation.composables

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun BaseDialog(
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    showDialog: Boolean,
    offsideDismiss: Boolean = true,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {

  //  val scrollState = rememberScrollState()

        AnimatedVisibility(
            visible = showDialog,
            enter = fadeIn(tween(250)) + scaleIn(tween(250)),
            exit = fadeOut(tween(250)) + scaleOut(tween(250))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))  // Fondo oscuro semitransparente
                    .pointerInput(Unit) {  // Bloquear eventos táctiles en el fondo
                        if (offsideDismiss) {
                            detectTapGestures { onDismissRequest() }
                        }
                    }

                ,
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = shape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .padding(20.dp),
                    shadowElevation = 8.dp  // Sombra para efecto de elevación
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        content()
                    }
                }
            }

            BackHandler(enabled = true) {
                onDismissRequest()
            }
        }
}

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