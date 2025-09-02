package com.nayibit.phrasalito_presentation.composables
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
    ) {
    val progress = if (total > 0) current.toFloat() / total else 0f

    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        LinearProgressIndicator(
            progress = { progress },
            modifier = modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }


}

@Preview
@Composable
fun ProgressBarPreview() {
    ProgressBar(current = 50, total = 100)
}